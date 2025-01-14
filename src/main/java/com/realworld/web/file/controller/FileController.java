package com.realworld.web.file.controller;

import com.realworld.application.file.service.FileService;
import com.realworld.common.response.SuccessResponse;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.common.response.code.SuccessResponseCode;
import com.realworld.common.swagger.ExceptionResponseAnnotations;
import com.realworld.common.swagger.SuccessResponseAnnotation;
import com.realworld.web.file.payload.request.FileDeleteRequest;
import com.realworld.web.file.payload.request.FileMoveRequest;
import com.realworld.web.file.payload.response.FileResponse;
import com.realworld.web.file.payload.response.FileResponses;
import com.realworld.web.file.payload.response.FileUrlResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Tag(
        name = "파일 관리",
        description = "파일 리사이즈, 업로드, 이동 및 삭제를 위한 API"
)
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private static final String SUCCESS_MESSAGE = "파일 업로드 성공";
    private static final int SUCCESS_STATUS = 200;

    private final FileService fileService;

    @SuccessResponseAnnotation(SuccessResponseCode.SUCCESS)
    @ExceptionResponseAnnotations({
            ExceptionResponseCode.UNSUPPORTED_FILE_IMAGE_TYPE_ERROR,
            ExceptionResponseCode.FILE_PROCESSING_ERROR,
            ExceptionResponseCode.FILE_IMAGE_RESIZE_ERROR
    })
    @PostMapping(
            value = "/upload/images/resize",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<SuccessResponse<FileResponses>> uploadResizedImage(
            @RequestPart("files") @Parameter(description = "업로드할 이미지 파일들 (다중 파일 가능)") MultipartFile[] files,
            @RequestParam(name = "destination_directory", required = false, defaultValue = "temporary") @Parameter(description = "저장할 대상 디렉터리 경로") String destinationDirectory,
            @RequestParam(defaultValue = "200") @Parameter(description = "리사이즈할 이미지의 너비") int width,
            @RequestParam(defaultValue = "200") @Parameter(description = "리사이즈할 이미지의 높이") int height
    ) {
        List<FileResponse> fileResponses = Arrays.stream(files)
                .map(file -> FileResponse.from(
                        fileService.saveResizedImage(
                                destinationDirectory,
                                file,
                                width,
                                height
                        )
                ))
                .toList();

        FileResponses responses = FileResponses.of(fileResponses);

        SuccessResponse<FileResponses> successResponse = new SuccessResponse<>(
                responses,
                SUCCESS_STATUS,
                HttpStatus.OK,
                SUCCESS_MESSAGE
        );

        return ResponseEntity.ok(successResponse);
    }

    @SuccessResponseAnnotation(SuccessResponseCode.SUCCESS)
    @ExceptionResponseAnnotations({
            ExceptionResponseCode.UNSUPPORTED_FILE_IMAGE_TYPE_ERROR,
            ExceptionResponseCode.FILE_PROCESSING_ERROR,
            ExceptionResponseCode.FILE_IMAGE_RESIZE_ERROR
    })
    @PostMapping(value = "/upload/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse<FileResponses>> uploadImage(
            @RequestPart @Parameter(description = "업로드할 이미지 파일들 (다중 파일 가능)") MultipartFile[] files,
            @RequestParam(name = "destination_directory", required = false, defaultValue = "temporary")
            @Parameter(description = "저장할 대상 디렉터리 경로 (기본값: 임시 디렉터리)") String destinationDirectory
    ) {
        List<FileResponse> fileResponses = Arrays.stream(files)
                .map(file -> FileResponse.from(
                        fileService.saveImage(
                                destinationDirectory,
                                file
                        )
                ))
                .toList();

        FileResponses responses = FileResponses.of(fileResponses);

        SuccessResponse<FileResponses> successResponse = new SuccessResponse<>(
                responses,
                SUCCESS_STATUS,
                HttpStatus.OK,
                SUCCESS_MESSAGE
        );

        return ResponseEntity.ok(successResponse);
    }

    @SuccessResponseAnnotation(SuccessResponseCode.SUCCESS)
    @ExceptionResponseAnnotations(ExceptionResponseCode.FILE_NOT_FOUND_ERROR)
    @PatchMapping("/move")
    public ResponseEntity<SuccessResponse<FileUrlResponses>> move(@RequestBody final FileMoveRequest request) {
        List<String> movedUrls = request.getUrls().stream()
                .map(url -> fileService.move(url, request.getDirectory()))
                .toList();

        FileUrlResponses responses = FileUrlResponses.of(movedUrls);

        SuccessResponse<FileUrlResponses> successResponse = new SuccessResponse<>(
                responses,
                SUCCESS_STATUS,
                HttpStatus.OK,
                "파일 이동 성공"
        );

        return ResponseEntity.ok(successResponse);
    }

    @DeleteMapping
    @SuccessResponseAnnotation(SuccessResponseCode.SUCCESS)
    @ExceptionResponseAnnotations(ExceptionResponseCode.FILE_NOT_FOUND_ERROR)
    public ResponseEntity<SuccessResponse<FileResponse>> delete(@RequestBody final FileDeleteRequest request) {
        request.getUrls().forEach(fileService::delete);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        null,
                        SUCCESS_STATUS,
                        HttpStatus.OK,
                        "파일 삭제 성공"
                )
        );
    }

}
