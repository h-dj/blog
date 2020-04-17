package cn.hdj.hdjblog.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author hdj
 * @ClassName: SwaggerConfig
 * @Description: swagger api生成工具配置
 * @Version 1.0
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {


    private final SystemProperties systemProperties;

    public SwaggerConfig(SystemProperties systemProperties) {
        this.systemProperties = systemProperties;
    }

    /**
     * 静态资源处理
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(systemProperties.isSwaggerEnabled())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.hdj.hdjblog"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Hdj's Blog API")
                .description("Hdj's Blog  API")
                .contact(new Contact("hdj", "http://localhost:8080", "1432517356@qq.com"))
                .license("MIT")
                .licenseUrl("")
                .version("1.0")
                .build();
    }
}
