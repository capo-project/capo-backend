package com.realworld.common.swagger;

import com.realworld.common.response.code.ExceptionResponseCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionResponseAnnotations {
    ExceptionResponseCode[] value();
}
