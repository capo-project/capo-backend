package com.realworld.web.file.payload.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Schema(
        description = "파일 이동 요청 DTO"
)
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FileMoveRequest {

    @Schema(
            description = "이동할 파일의 URL 목록",
            example = "[\"https://example.com/file1.jpg\", \"https://example.com/file2.png\"]"
    )
    @NotEmpty(message = "urls는 필수 입력값이며, 최소 하나 이상의 URL을 포함해야 합니다.")
    private final List<String> urls;

    @Schema(
            description = "파일이 이동될 대상 디렉토리 경로",
            example = "archive"
    )
    @NotNull(message = "directory는 필수 입력 값입니다.")
    private final String directory;

    @JsonCreator
    public FileMoveRequest(final List<String> urls, final String directory) {
        this.urls = Collections.unmodifiableList(urls);
        this.directory = directory;
    }

}
