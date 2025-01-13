package com.realworld.web.file.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Schema(
        description = "파일 응답 목록 DTO"
)
@Getter
public class FileResponses {

    @Schema(
            description = "파일 응답 객체들의 목록"
    )
    private final List<FileResponse> files;

    private FileResponses(List<FileResponse> files) {
        this.files = Collections.unmodifiableList(files);
    }

    public static FileResponses of(List<FileResponse> fileResponses) {
        return new FileResponses(fileResponses);
    }

}
