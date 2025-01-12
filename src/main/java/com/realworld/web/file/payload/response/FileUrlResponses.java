package com.realworld.web.file.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Schema(
        description = "파일 URL 응답 목록 DTO"
)
@Getter
public class FileUrlResponses {

    @Schema(
            description = "이동된 파일의 URL 목록",
            example = "['http://example.com/archive/file1.jpg', 'http://example.com/archive/file2.png']"
    )
    private final List<String> fileUrls;

    private FileUrlResponses (List<String> fileUrls) {
        this.fileUrls = Collections.unmodifiableList(fileUrls);
    }

    public static FileUrlResponses of (List<String> fileUrlResponses) {
        return new FileUrlResponses(fileUrlResponses);
    }

}
