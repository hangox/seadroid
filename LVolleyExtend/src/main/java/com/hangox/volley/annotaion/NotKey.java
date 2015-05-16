package com.hangox.volley.annotaion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created With Android Studio
 * User hangox
 * Date 15/5/5
 * Time 下午8:27
 * 如果参数什么都不是，请加上
 */

@Documented
@Target(ElementType.FIELD)
@Retention(RUNTIME)
public @interface NotKey {
}
