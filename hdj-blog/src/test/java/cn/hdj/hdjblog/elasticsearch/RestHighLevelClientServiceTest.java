package cn.hdj.hdjblog.elasticsearch;

import cn.hdj.hdjblog.HdjBlogApplicationTests;
import cn.hdj.hdjblog.constaint.EsConstaints;
import cn.hdj.hdjblog.entity.ArticleDO;
import cn.hutool.json.JSONUtil;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;

import java.io.IOException;

/**
 * @author hdj
 * @version 1.0
 * @date 19/01/2020 14:51
 * @description:
 */
public class RestHighLevelClientServiceTest extends HdjBlogApplicationTests {


    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RestHighLevelClient client;

    @Test
    public void createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("twitter");
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );

        request.mapping(
                "{\n" +
                        "  \"properties\": {\n" +
                        "    \"message\": {\n" +
                        "      \"type\": \"text\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}",
                XContentType.JSON);

        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    public void addDoc() throws IOException {
        IndexRequest request = new IndexRequest("article_index");
        request.id("123456789");
        ArticleDO articleDO = new ArticleDO();
        articleDO.setContent("123");
        articleDO.setId(132L);
        articleDO.setTags("afdsafd");

        request.source(JSONUtil.toJsonStr(articleDO), XContentType.JSON);
        client.index(request, RequestOptions.DEFAULT);
    }



    @Autowired
    private ElasticsearchRestTemplate restTemplate;


    @Test
    public void searchDoc() {

        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("博客系统")
                .field("description")
                .field("content")
                .field("title");

        StringQuery query = new StringQuery(queryBuilder.toString());

        //分页排序
        Sort sort = Sort.by(Sort.Order.desc("updateTime"));
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        query.setPageable(pageRequest);

        SearchHits<ArticleDO> search = restTemplate.search(query,
                ArticleDO.class,
                IndexCoordinates.of(EsConstaints.ES_INDEX_ARTICLE)
        );

        System.out.println(search);
        search.get()
                .forEach(articleDTOSearchHit -> {
                    System.out.println(articleDTOSearchHit);
                });
    }

}