package com.realworld.common.swagger;

import io.swagger.v3.oas.annotations.media.Content;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface SwaggerRequestBody {
    // 리퀴스트 설명
    String description() default "";

    // 리퀴스트 바디가 필수이면 true 아니면 false
    boolean required() default true;

    // 리퀴스트 바디의 미디어 타입을 지정 기존을 json으로 하도록 했음
    Content[] content() default @Content(mediaType = "application/json");

}
