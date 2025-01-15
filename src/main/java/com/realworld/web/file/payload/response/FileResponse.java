package com.realworld.web.file.payload.response;

import com.realworld.feature.file.entity.File;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(
        description = "파일 응답 DTO"
)
@Getter
public class FileResponse {

    @Schema(
            description = "파일 이름 (UUID 형식)",
            example = "550e8400-e29b-41d4-a716-446655440000.jpg"
    )
    private final String name;

    @Schema(
            description = "파일의 컨텐트 타입",
            example = "image/jpeg"
    )
    private final String contentType;

    @Schema(
            description = "파일 크기 (Byte 단위)",
            example = "204800"
    )
    private final long size;

    @Schema(
            description = "파일 접근 URL",
            example = "http://example.com/file1.jpg"
    )
    private final String url;

    @Builder
    private FileResponse(String name, String contentType, long size, String url) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.url = url;
    }

    public static FileResponse from(File file) {
        return FileResponse.builder()
                .name(file.getDetails().getName())
                .contentType(file.getDetails().getContentType())
                .size(file.getDetails().getSize())
                .url(file.getUrl())
                .build();
    }

}
