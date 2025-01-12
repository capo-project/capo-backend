package com.realworld.web.file.payload.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Schema(
        description = "파일 삭제 요청 DTO"
)
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FileDeleteRequest {

    @Schema(
            description = "삭제할 파일의 URL 목록",
            example = "['http://example.com/file1.jpg', 'http://example.com/file2.png']"
    )
    @NotEmpty(message = "urls는 필수 입력값이며, 최소 하나 이상의 URL을 포함해야 합니다.")
    private final List<String> urls;

    @JsonCreator
    public FileDeleteRequest(final List<String> urls) {
        this.urls = Collections.unmodifiableList(urls);
    }

}
