package cn.hdj.hdjblog.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @author hdj
 * @version 1.0
 * @date 19/01/2020 13:38
 * @description: 搜索引擎配置
 */
//@Configuration
public class EsConfig  {

    @Autowired
    private SystemProperties systemProperties;


    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {

        SystemProperties.Elasticsearch elasticsearch = systemProperties.getElasticsearch();
        String[] clusterNodes = elasticsearch.getClusterNodes();
        HttpHost[] httpHosts = new HttpHost[clusterNodes.length];
        for (int i = 0; i < clusterNodes.length; i++) {
            httpHosts[i] = HttpHost.create(clusterNodes[i]);
        }
       //凭证注册器
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //注册
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(elasticsearch.getUserName(), elasticsearch.getPassword()));
        RestClientBuilder builder = RestClient.builder(httpHosts)
                .setHttpClientConfigCallback(httpAsyncClientBuilder ->
                        httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        return new RestHighLevelClient(builder);
    }

}
