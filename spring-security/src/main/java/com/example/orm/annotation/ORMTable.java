package com.example.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用来设置表名
 */
@Retention(RetentionPolicy.RUNTIME)    //运行期间保留注解的信息
@Target(ElementType.TYPE)   //用在类上
public @interface ORMTable {
    public String name() default "";
}