package cn.hdj.hdjblog.elasticsearch;

import cn.hdj.hdjblog.exception.MyException;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author hdj
 * @version 1.0
 * @date 19/01/2020 14:41
 * @description:
 */
public class RestHighLevelClientRepository<T extends Serializable> {

    protected RestHighLevelClient client;
    protected final static ObjectMapper objectMapper;

    static {
        objectMapper = Jackson2ObjectMapperBuilder.json().build();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //前端Long类型精度丢失
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
    }

    @Autowired
    public void setClient(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     * 获取泛型class类型
     *
     * @return
     */
    protected Class<?> getEntityClass() {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                return (Class<?>) actualTypeArguments[0];
            }
        }
        return null;
    }


    /**
     * 创建索引
     *
     * @param indexName
     * @param settings
     * @param mapping
     * @return
     * @throws IOException
     */
    public CreateIndexResponse createIndex(String indexName, String settings, String mapping) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        if (StrUtil.isNotEmpty(settings)) {
            request.settings(settings, XContentType.JSON);
        }
        if (StrUtil.isNotEmpty(mapping)) {
            request.mapping(mapping, XContentType.JSON);
        }
        return client.indices()
                .create(request, RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     *
     * @param indexNames
     * @return
     * @throws IOException
     */
    public AcknowledgedResponse deleteIndex(String... indexNames) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(indexNames);
        return client.indices().delete(request, RequestOptions.DEFAULT);
    }


    /**
     * 判断 index 是否存在
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public boolean indexExists(String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 根据 id 删除指定索引中的文档
     *
     * @param indexName
     * @param id
     * @return
     * @throws IOException
     */
    public DeleteResponse deleteDoc(String indexName, String id) throws IOException {
        DeleteRequest request = new DeleteRequest(indexName, id);
        return client.delete(request, RequestOptions.DEFAULT);
    }

    /**
     * 判断索引中是否存在给定id 文档
     *
     * @param indexName 索引名称
     * @param id        文档id
     * @return
     */
    public boolean existDoc(String indexName, String id) throws IOException {
        GetRequest getRequest = new GetRequest(
                indexName,
                id);
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        return client.exists(getRequest, RequestOptions.DEFAULT);
    }
    /**
     * 根据 id 更新指定索引中的文档
     *
     * @param indexName
     * @param id
     * @return
     * @throws IOException
     */
    public UpdateResponse updateDoc(String indexName, String id, T source) throws IOException {
        UpdateRequest request = new UpdateRequest(indexName, id);
        String value = objectMapper.writeValueAsString(source);
        request.doc(XContentType.JSON, value);
        return client.update(request, RequestOptions.DEFAULT);
    }

    /**
     * 根据 id 更新指定索引中的文档
     *
     * @param indexName
     * @param id
     * @return
     * @throws IOException
     */
    public UpdateResponse updateDoc(String indexName, String id, Map<String, Object> updateMap) throws IOException {
        UpdateRequest request = new UpdateRequest(indexName, id);
        request.doc(updateMap);
        return client.update(request, RequestOptions.DEFAULT);
    }

    /**
     * 根据某字段的 k-v 更新索引中的文档
     *
     * @param fieldName
     * @param value
     * @param indexName
     * @throws IOException
     */
    public void updateByQuery(String fieldName, String value, String... indexName) throws IOException {
        UpdateByQueryRequest request = new UpdateByQueryRequest(indexName);
        //单次处理文档数量
        request.setBatchSize(100)
                .setQuery(new TermQueryBuilder(fieldName, value))
                .setTimeout(TimeValue.timeValueMinutes(2));
        client.updateByQuery(request, RequestOptions.DEFAULT);
    }

    /**
     * 添加文档 手动指定id
     *
     * @param indexName
     * @param id
     * @param source
     * @return
     * @throws IOException
     */
    public IndexResponse addDoc(String indexName, String id, T source) throws IOException {
        IndexRequest request = new IndexRequest(indexName);
        if (null != id) {
            request.id(id);
        }
        String value = objectMapper.writeValueAsString(source);
        request.source(value, XContentType.JSON);
        return client.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 添加文档 使用自动id
     *
     * @param indexName
     * @param source
     * @return
     * @throws IOException
     */
    public IndexResponse addDoc(String indexName, T source) throws IOException {
        return addDoc(indexName, null, source);
    }

    /**
     * 简单模糊匹配 默认分页为 0,10
     *
     * @param field
     * @param key
     * @param page
     * @param size
     * @param indexNames
     * @return
     * @throws IOException
     */
    public SearchResponse search(String field, String key, int page, int size, String... indexNames) throws IOException {
        SearchRequest request = new SearchRequest(indexNames);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(new MatchQueryBuilder(field, key))
                .from(page)
                .size(size);
        request.source(builder);
        return client.search(request, RequestOptions.DEFAULT);
    }

    /**
     * term 查询 精准匹配
     *
     * @param field
     * @param key
     * @param page
     * @param size
     * @param indexNames
     * @return
     * @throws IOException
     */
    public SearchResponse termSearch(String field, String key, int page, int size, String... indexNames) throws IOException {
        SearchRequest request = new SearchRequest(indexNames);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termsQuery(field, key))
                .from(page)
                .size(size);
        request.source(builder);
        return client.search(request, RequestOptions.DEFAULT);
    }

    /**
     * 通用查询
     * @param queryBuilder
     * @param page
     * @param size
     * @param indexNames
     * @return
     * @throws IOException
     */
    public SearchResponse search(QueryBuilder queryBuilder, int page, int size, String... indexNames) throws IOException {
        SearchRequest request = new SearchRequest(indexNames);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(queryBuilder)
                .from(page)
                .size(size);
        request.source(builder);
        return client.search(request, RequestOptions.DEFAULT);
    }

    /**
     * 批量导入
     *
     * @param indexName
     * @param isAutoId  使用自动id 还是使用传入对象的id
     * @param sources
     * @return
     * @throws IOException
     */
    public BulkResponse importAll(String indexName, boolean isAutoId, List<T> sources) throws IOException, IllegalAccessException {
        if (CollectionUtil.isEmpty(sources)) {
            throw new MyException("导入es数据不能为空！");
        }
        BulkRequest request = new BulkRequest();
        for (T source : sources) {
            String value = objectMapper.writeValueAsString(source);
            if (isAutoId) {
                request.add(new IndexRequest(indexName).source(value, XContentType.JSON));
            } else {
                Field field = ReflectionUtils.findField(source.getClass(), "id", Long.class);
                ReflectionUtils.makeAccessible(field);
                Long id = (Long) ReflectionUtils.getField(field, source);
                request.add(new IndexRequest(indexName).id(String.valueOf(id)).source(value, XContentType.JSON));
            }
        }
        return client.bulk(request, RequestOptions.DEFAULT);
    }
}
