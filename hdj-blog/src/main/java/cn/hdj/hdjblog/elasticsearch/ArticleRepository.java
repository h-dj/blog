package cn.hdj.hdjblog.elasticsearch;

import cn.hdj.hdjblog.constaint.EsConstaints;
import cn.hdj.hdjblog.model.dto.ArticleDTO;
import cn.hdj.hdjblog.model.vo.PageVO;
import cn.hutool.core.util.StrUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hdj
 * @version 1.0
 * @date 19/01/2020 19:59
 * @description:
 */
@Service
public class ArticleRepository extends RestHighLevelClientRepository<ArticleDTO> {

    /**
     * 查询
     *
     * @param keywords
     * @param page
     * @param pageSize
     * @return
     */
    public PageVO search(String keywords, Integer page, Integer pageSize) throws IOException {
        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(keywords)
                .field("description")
                .field("content")
                .field("title");
        int from = (page - 1) * pageSize;
        SearchResponse response = this.search(queryBuilder, from, pageSize, EsConstaints.ES_INDEX_ARTICLE);
        long totalHits = response.getHits().getTotalHits().value;
        List<ArticleDTO> results = new ArrayList<>(Long.valueOf(totalHits).intValue());

        for (SearchHit hit : response.getHits()) {
            if (hit != null) {
                ArticleDTO result = null;
                if (StrUtil.isNotEmpty(hit.getSourceAsString())) {
                    result = objectMapper.readValue(hit.getSourceAsString(), ArticleDTO.class);
                    results.add(result);
                }
            }
        }
        return PageVO.build(results, response.getHits().getTotalHits().value, pageSize, pageSize);
    }
}
