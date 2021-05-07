package com.example.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来设置字段名
 */
@Retention(RetentionPolicy.RUNTIME)    //运行期间保留注解的信息
@Target(ElementType.FIELD)
public @interface ORMColumn {
    public String name() default "";
}
