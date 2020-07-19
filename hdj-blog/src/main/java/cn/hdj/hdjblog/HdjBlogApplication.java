package cn.hdj.hdjblog;

import cn.hdj.hdjblog.config.SystemProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * @author hdj
 * @ClassName: HdjBlogApplication
 * @Description: Spring boot 启动类
 * @Version 1.0
 */
@EnableAsync
@EnableConfigurationProperties(SystemProperties.class)
@MapperScan("cn.hdj.hdjblog.dao")
@SpringBootApplication
public class HdjBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(HdjBlogApplication.class, args);
    }
}
