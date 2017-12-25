package com.example.yfhlibrary.qi;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by User2 on 2017/12/7.
 */

@Retention(RUNTIME) @Target(FIELD)
public @interface QiAutowired {
}
