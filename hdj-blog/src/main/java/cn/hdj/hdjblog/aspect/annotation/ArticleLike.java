package cn.hdj.hdjblog.aspect.annotation;

import java.lang.annotation.*;

/**
 * @author hdj
 * @version 1.0
 * @date 30/05/2020 22:34
 * @description: 文章点赞标识注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ArticleLike {
}
