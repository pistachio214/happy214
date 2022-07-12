package com.happy.lucky.framework.annotation;

import java.lang.annotation.*;

/**
 * 自定义日志类注解
 *
 * @author Pengsy
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
public @interface OperLog {

    // 操作模块
    String operModul() default "";

    // 操作类型
    String operType() default "";

    // 操作说明
    String operDesc() default "";
}
