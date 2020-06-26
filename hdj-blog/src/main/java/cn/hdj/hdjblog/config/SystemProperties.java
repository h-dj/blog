package cn.hdj.hdjblog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

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
     * Oss存储
     */
    private Oss oss;

    @Data
    public static class Oss implements Serializable {
        private Qiniu qiniu;
    }

    @Data
    public static class Qiniu implements Serializable {
        /**
         * 域名
         */
        private String domain;
        /**
         * 前缀
         */
        private String prefix;
        /**
         * 访问key
         */
        private String accessKey;
        /**
         * 秘钥
         */
        private String secretKey;
        /**
         * 桶名称
         */
        private String bucketName;
    }


}
