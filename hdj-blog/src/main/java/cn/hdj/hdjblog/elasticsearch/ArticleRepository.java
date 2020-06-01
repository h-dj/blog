package cn.hdj.hdjblog.elasticsearch;

import cn.hdj.hdjblog.constaint.EsConstaints;
import cn.hdj.hdjblog.entity.ArticleDO;
import cn.hdj.hdjblog.model.vo.PageVO;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.util.List;
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


        SearchHits<ArticleDO> search = restTemplate.search(query,
                ArticleDO.class,
                IndexCoordinates.of(EsConstaints.ES_INDEX_ARTICLE));

        List<ArticleDO> list = search.get()
                .map(articleDTOSearchHit -> articleDTOSearchHit.getContent())
                .collect(Collectors.toList());
        return PageVO.build(list, search.getTotalHits(), pageSize, pageSize);
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
