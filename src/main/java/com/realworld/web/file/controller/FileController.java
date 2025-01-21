package com.realworld.web.file.controller;

import com.realworld.application.file.service.FileService;
import com.realworld.common.response.SuccessResponse;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.common.response.code.SuccessCode;
import com.realworld.common.annotation.swagger.ExceptionResponseAnnotations;
import com.realworld.common.annotation.swagger.SuccessResponseAnnotation;
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

import java.util.ArrayList;
import java.util.List;

@Tag(
        name = "파일 관리",
        description = "파일 리사이즈, 업로드, 이동 및 삭제를 위한 API"
)
@RestController
@RequestMapping("/V2/files")
@RequiredArgsConstructor
public class FileController {

    private static final String SUCCESS_MESSAGE = "파일 업로드 성공";
    private static final int SUCCESS_STATUS = 200;

    private final FileService fileService;

    @SuccessResponseAnnotation(SuccessCode.SUCCESS)
    @ExceptionResponseAnnotations({
            ErrorCode.UNSUPPORTED_FILE_IMAGE_TYPE_ERROR,
            ErrorCode.FILE_PROCESSING_ERROR,
            ErrorCode.FILE_IMAGE_RESIZE_ERROR,
            ErrorCode.FILE_IMAGE_PROCESSING_ERROR
    })
    @PostMapping(
            value = "/upload/images/resize",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<SuccessResponse<FileResponses>> uploadResizedImage(
            @RequestPart("files") @Parameter(description = "업로드할 이미지 파일들 (다중 파일 가능)") MultipartFile[] files,
            @RequestParam(name = "target_directory", required = false, defaultValue = "temporary") @Parameter(description = "저장할 대상 디렉터리 경로") String targetDir,
            @RequestParam(defaultValue = "200") @Parameter(description = "리사이즈할 이미지의 너비") int width,
            @RequestParam(defaultValue = "200") @Parameter(description = "리사이즈할 이미지의 높이") int height
    ) {
        List<FileResponse> fileResponses = new ArrayList<>(files.length);
        for (MultipartFile file : files) {
            FileResponse response = FileResponse.from(
                    fileService.saveResizedImage(
                            targetDir,
                            file,
                            width,
                            height
                    )
            );
            fileResponses.add(response);
        }

        FileResponses responses = FileResponses.of(fileResponses);
        SuccessResponse<FileResponses> successResponse = new SuccessResponse<>(
                responses,
                SUCCESS_STATUS,
                HttpStatus.OK,
                SUCCESS_MESSAGE
        );

        return ResponseEntity.ok(successResponse);
    }

    @SuccessResponseAnnotation(SuccessCode.SUCCESS)
    @ExceptionResponseAnnotations({
            ErrorCode.UNSUPPORTED_FILE_IMAGE_TYPE_ERROR,
            ErrorCode.FILE_PROCESSING_ERROR,
    })
    @PostMapping(value = "/upload/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse<FileResponses>> uploadImage(
            @RequestPart @Parameter(description = "업로드할 이미지 파일들 (다중 파일 가능)") MultipartFile[] files,
            @RequestParam(name = "target_directory", required = false, defaultValue = "temporary") @Parameter(description = "저장할 대상 디렉터리 경로 (기본값: 임시 디렉터리)") String targetDir
    ) {
        List<FileResponse> fileResponses = new ArrayList<>(files.length);
        for (MultipartFile file : files) {
            FileResponse response = FileResponse.from(
                    fileService.saveImage(targetDir, file)
            );
            fileResponses.add(response);
        }

        FileResponses responses = FileResponses.of(fileResponses);
        SuccessResponse<FileResponses> successResponse = new SuccessResponse<>(
                responses,
                SUCCESS_STATUS,
                HttpStatus.OK,
                SUCCESS_MESSAGE
        );

        return ResponseEntity.ok(successResponse);
    }

    @SuccessResponseAnnotation(SuccessCode.SUCCESS)
    @ExceptionResponseAnnotations(ErrorCode.FILE_NOT_FOUND_ERROR)
    @PatchMapping("/move")
    public ResponseEntity<SuccessResponse<FileUrlResponses>> move(@RequestBody final FileMoveRequest request) {
        List<String> movedUrls = new ArrayList<>(request.getUrls().size());
        for (String url : request.getUrls()) {
            fileService.move(url, request.getDirectory());
            movedUrls.add(url);
        }

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
    @SuccessResponseAnnotation(SuccessCode.SUCCESS)
    @ExceptionResponseAnnotations(ErrorCode.FILE_NOT_FOUND_ERROR)
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
