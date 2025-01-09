package com.realworld.common.swagger;


import com.realworld.common.response.code.SuccessResponseCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SuccessResponseAnnotation {
    SuccessResponseCode value() default SuccessResponseCode.SUCCESS;

}
