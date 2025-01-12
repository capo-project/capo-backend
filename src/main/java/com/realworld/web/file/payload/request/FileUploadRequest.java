package com.realworld.web.file.payload.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Schema(
        description = "파일 업로드 요청 DTO"
)
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FileUploadRequest {

    @Schema(
            description = "파일이 저장될 대상 디렉토리 경로",
            example = "profile"
    )
    @NotNull(message = "destination_directory는 필수 입력 값이다.")
    private final String destinationDirectory;

    @Schema(
            description = "업로드할 이미지 파일들",
            example = "file1.jpg, file2.png"
    )
    @Size(min = 1, message = "적어도 하나의 파일을 업로드해야 한다.")
    @NotNull(message  = "multipart_files_는 필수 입력값입니다.")
    private final MultipartFile[] multipartFiles;

    @Schema(
            description = "이미지 리사이즈 시 너비",
            example = "800"
    )
    private final Integer width;

    @Schema(
            description = "이미지 리사이즈 시 높이",
            example = "600"
    )
    private final Integer height;

    @JsonCreator
    public FileUploadRequest(final MultipartFile[] multipartFiles, final String destinationDirectory, final Integer width, final Integer height) {
        this.multipartFiles = multipartFiles;
        this.destinationDirectory = destinationDirectory;
        this.width = width;
        this.height = height;
    }

}
