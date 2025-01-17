package com.realworld.common.annotation.swagger;


import com.realworld.common.response.code.SuccessCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SuccessResponseAnnotation {
    SuccessCode value() default SuccessCode.SUCCESS;

}
