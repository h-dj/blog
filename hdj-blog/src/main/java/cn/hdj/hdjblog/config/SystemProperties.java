package cn.hdj.hdjblog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hdj
 * @version 1.0
 * @date 12/7/19 11:58 PM
 * @description: 系统的配置参数
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "blog")
public class SystemProperties {

    /**
     * 可跨域源
     */
    private String[] corsAllowedOrigins;
    /**
     * 是否开启swagger
     */
    private boolean swaggerEnabled;

    /**
     * 文件上传存放路径
     */
    private String fileUploadDir;
    /**
     * 文件访问基础路径
     */
    private String fileBaseUrl;

    private Elasticsearch elasticsearch = new Elasticsearch();

    @Data
    public class Elasticsearch {
        String userName;
        String password;
        String[] clusterNodes;
    }
}
