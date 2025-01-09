package com.realworld.common.swagger;

import com.realworld.common.response.SuccessResponse;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.common.response.code.SuccessResponseCode;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import lombok.SneakyThrows;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

@OpenAPIDefinition(
        info = @Info(
                title = "capo documentation",
                version = "1.0",
                description = "연예인 포토카드 api 스웨거 명세서"
        )
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Configuration
public class SwaggerConfigV3 {
    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            // @SuccessResponseAnnotation 추가
            SuccessResponseAnnotation successAnnotation = handlerMethod.getMethodAnnotation(SuccessResponseAnnotation.class);
            // 응답 코드 설정
            SuccessResponseCode successResponseCode = Optional.ofNullable(successAnnotation).map(SuccessResponseAnnotation::value).orElse(SuccessResponseCode.SUCCESS);
            this.addResponseBodyWrapperSchema(operation, SuccessResponse.class, "result", successResponseCode);

            ExceptionResponseAnnotations exceptionAnnotation = handlerMethod.getMethodAnnotation(ExceptionResponseAnnotations.class);
            if (exceptionAnnotation != null) {
                this.addExceptionResponses(operation, exceptionAnnotation);
            }

            for (MethodParameter parameter : handlerMethod.getMethodParameters()) {
                SwaggerRequestBody swaggerRequestBody = parameter.getMethodAnnotation(SwaggerRequestBody.class);
                if (swaggerRequestBody != null) {
                    RequestBody requestBody = new RequestBody()
                            .description(swaggerRequestBody.description())
                            .required(swaggerRequestBody.required());

                    Content content = getContent(swaggerRequestBody);
                    requestBody.setContent(content);

                    operation.setRequestBody(requestBody);
                }
            }

            operation.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"));
            return operation;
        };
    }

    private Content getContent(SwaggerRequestBody swaggerRequestBody) {
        Content content = new Content();
        MediaType mediaType = new MediaType();

        if (swaggerRequestBody.content().length > 0 && swaggerRequestBody.content()[0].schema() != null) {
            // 커스텀 어노테이션의 첫 번째 content에서 schema 설정 가져오기
            Schema<?> schema = new Schema<>();

            Class<?> schemaImplementation = swaggerRequestBody.content()[0].schema().implementation();
            schema.set$ref(schemaImplementation.getSimpleName());
            mediaType.setSchema(schema);
        }

        content.addMediaType("application/json", mediaType);
        return content;
    }

    @SneakyThrows
    private <T> void addResponseBodyWrapperSchema(Operation operation, Class<T> type, String wrapFieldName, SuccessResponseCode successResponseCode) {
        ApiResponses responses = operation.getResponses();

        String responseCode = String.valueOf(successResponseCode.getHttpStatus().value());

        if (!"200".equals(responseCode)) {
            ApiResponse existingResponse = responses.get("200");
            this.changeResponseCode(existingResponse, responses, responseCode);
        }

        ApiResponse response = responses.computeIfAbsent(String.valueOf(successResponseCode.getHttpStatus()), key -> new ApiResponse());
        response.setDescription(successResponseCode.getMessage());
        Content content = response.getContent();

        if (content != null) {
            content.keySet().forEach(key -> {
                MediaType mediaType = content.get(key);
                mediaType.setSchema(wrapSchema(mediaType.getSchema(), type, wrapFieldName, successResponseCode));
            });
        }
    }

    private void changeResponseCode(ApiResponse existingResponse, ApiResponses responses, String responseCode) {
        if (existingResponse != null) {
            ApiResponse newResponse = new ApiResponse()
                    .description(existingResponse.getDescription())
                    .content(existingResponse.getContent());

            responses.addApiResponse(responseCode, newResponse);
            responses.remove("200");
        }
    }

    @SneakyThrows
    private <T> Schema<T> wrapSchema(Schema<?> originalSchema, Class<T> type, String wrapFieldName, SuccessResponseCode successResponseCode) {
        Schema<T> wrapperSchema = new Schema<>();
        T instance = type.getDeclaredConstructor().newInstance();

        for (Field field : type.getDeclaredFields()) {
            String fieldName = field.getName();
            String getterName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            Method getterMethod = type.getMethod(getterName);

            Object value = getterMethod.invoke(instance);

            Schema<?> fieldSchema = new Schema<>().example(value);
            if (fieldName.equals("result")) {
                fieldSchema = originalSchema;
            } else if (fieldName.equals("resultCode")) {
                fieldSchema.example(successResponseCode.getResultCode());
            } else if (fieldName.equals("resultMsg")) {
                fieldSchema.example(successResponseCode.getMessage());
            } else if (fieldName.equals("httpStatus")) {
                fieldSchema.example(successResponseCode.getHttpStatus());
            }

            wrapperSchema.addProperty(fieldName, fieldSchema);
        }

        wrapperSchema.addProperty(wrapFieldName, originalSchema);

        return wrapperSchema;
    }

    private void addExceptionResponses(Operation operation, ExceptionResponseAnnotations exceptionResponseAnnotations) {
        for (ExceptionResponseCode exceptionCode : exceptionResponseAnnotations.value()) {
            this.addExceptionResponse(operation, exceptionCode);
        }

    }

    private void addExceptionResponse(Operation operation, ExceptionResponseCode exceptionResponseCode) {
        ApiResponses responses = operation.getResponses();
        String responseCodeKey = String.valueOf(exceptionResponseCode.getHttpStatus().value());
        ApiResponse response = new ApiResponse()
                .description(exceptionResponseCode.getMessage());

        Content content = new Content();
        MediaType mediaType = new MediaType();
        mediaType.setSchema(createExceptionSchema(exceptionResponseCode));
        content.addMediaType("application/json", mediaType);
        response.setContent(content);

        responses.addApiResponse(responseCodeKey + "_" + exceptionResponseCode.name(), response);
    }

    private <T> Schema<T> createExceptionSchema(ExceptionResponseCode exceptionResponseCode) {
        Schema<T> exceptionSchema = new Schema<>();
        exceptionSchema.addProperty("httpStatus", new Schema<>().example(exceptionResponseCode.getHttpStatus().toString()));
        exceptionSchema.addProperty("resultCode", new Schema<>().example(exceptionResponseCode.getResultCode()));
        exceptionSchema.addProperty("resultMsg", new Schema<>().example(exceptionResponseCode.getMessage()));

        return exceptionSchema;
    }

}
