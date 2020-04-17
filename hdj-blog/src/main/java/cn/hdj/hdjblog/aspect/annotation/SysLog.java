package cn.hdj.hdjblog.aspect.annotation;

import cn.hdj.hdjblog.constaint.enums.LevelEnum;

import java.lang.annotation.*;

/**
 * @author hdj
 * @Description:　日志记录注解
 * @date 9/25/19
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SysLog {
    /**
     * 描述
     *
     * @return
     */
    String value() default "";

    /**
     * 是否忽略
     *
     * @return
     */
    boolean ignore() default false;

    /**
     * 是否忽略参数
     *
     * @return
     */
    boolean ignoreParams() default false;

    /**
     * 日志等级
     *
     * @return
     */
    LevelEnum logLevel() default LevelEnum.INFO;


}
