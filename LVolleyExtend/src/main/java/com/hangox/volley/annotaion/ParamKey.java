package com.hangox.volley.annotaion;

import java.lang.annotation.*;

/**
 * Created by hangox on 15/5/5.
 * param key
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamKey {
    String value() default "";
}
