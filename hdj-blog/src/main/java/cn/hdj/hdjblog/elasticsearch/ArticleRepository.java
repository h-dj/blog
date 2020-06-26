package cn.hdj.hdjblog.elasticsearch;

import cn.hdj.hdjblog.constaint.EsConstaints;
import cn.hdj.hdjblog.entity.ArticleDO;
import cn.hdj.hdjblog.model.vo.PageVO;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hdj
 * @version 1.0
 * @date 19/01/2020 19:59
 * @description:
 */
@Service
public class ArticleRepository {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    /**
     * 查询
     *
     * @param keywords
     * @param page
     * @param pageSize
     * @return
     */
    public PageVO pageSearch(String keywords, Integer page, Integer pageSize) {
        keywords = Optional.ofNullable(keywords).orElse(StrUtil.EMPTY);
        //多字段检索
        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(keywords)
                .field("description")
                .field("content")
                .field("title");

        StringQuery query = new StringQuery(queryBuilder.toString());
        //分页排序
        Sort sort = Sort.by(Sort.Order.desc("updateTime"));
        page = Math.max(0, page - 1);
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        query.setPageable(pageRequest);


        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("description")
                .field("content")
                .field("title")
                .postTags("</i>")
                .preTags("<i class='hdj-hl'>");
        HighlightQuery highlightQuery = new HighlightQuery(highlightBuilder);
        query.setHighlightQuery(highlightQuery);


        SearchHits<ArticleDO> search = restTemplate.search(query,
                ArticleDO.class,
                IndexCoordinates.of(EsConstaints.ES_INDEX_ARTICLE));

        List<SearchHit<ArticleDO>> searchHits = search.getSearchHits();
        if (CollectionUtil.isEmpty(searchHits)) {
            return PageVO.build(Collections.EMPTY_LIST, 0, pageSize, page);
        }

        List<ArticleDO> list = searchHits.stream()
                .map(articleDOSearchHit -> {
                    Map<String, List<String>> highlightFields = articleDOSearchHit.getHighlightFields();
                    articleDOSearchHit.getContent().setContent(null);
                    if (CollectionUtil.isNotEmpty(highlightFields.get("title"))) {
                        articleDOSearchHit.getContent().setTitle(highlightFields.get("title").get(0));
                    }
                    if (CollectionUtil.isNotEmpty(highlightFields.get("description"))) {
                        articleDOSearchHit.getContent().setDescription(highlightFields.get("description").get(0));
                    } else if (CollectionUtil.isNotEmpty(highlightFields.get("content"))) {
                        articleDOSearchHit.getContent().setDescription(highlightFields.get("content").get(0));
                    }
                    return articleDOSearchHit.getContent();
                })
                .collect(Collectors.toList());

        return PageVO.build(list, search.getTotalHits(), pageSize, page);
    }

    /**
     * 批量刷新索引
     *
     * @param list
     */
    public void refreshIndexBulk(List<ArticleDO> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            List<IndexQuery> indexQueries = list.stream()
                    .map(articleDO -> new IndexQueryBuilder()
                            .withId(StrUtil.toString(articleDO.getId()))
                            .withObject(articleDO)
                            .build())
                    .collect(Collectors.toList());
            restTemplate.bulkIndex(indexQueries,
                    IndexCoordinates.of(EsConstaints.ES_INDEX_ARTICLE));
        }
    }
}
