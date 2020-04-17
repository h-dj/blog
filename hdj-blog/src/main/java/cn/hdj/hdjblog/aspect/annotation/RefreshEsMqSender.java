package cn.hdj.hdjblog.aspect.annotation;

import java.lang.annotation.*;

/**
 * @author hdj
 * @version 1.0
 * @date 20/01/2020 21:37
 * @description: 刷新文章索引注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RefreshEsMqSender {
    /**
     * 消息
     * @return
     */
    String msg();
}
