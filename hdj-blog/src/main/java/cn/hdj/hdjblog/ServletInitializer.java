package cn.hdj.hdjblog;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * @author hdj
 * @Description: 用于打包部署时，初始化
 * 如果使用内嵌Tomcat, 则不需要
 * @Version 1.0
 */
@Configuration
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HdjBlogApplication.class);
    }
}
