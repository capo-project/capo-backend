package com.realworld.web.file.controller;

import com.realworld.application.file.service.FileImageService;
import com.realworld.common.response.SuccessResponse;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.common.response.code.SuccessResponseCode;
import com.realworld.common.swagger.ExceptionResponseAnnotations;
import com.realworld.common.swagger.SuccessResponseAnnotation;
import com.realworld.web.file.payload.request.FileDeleteRequest;
import com.realworld.web.file.payload.request.FileMoveRequest;
import com.realworld.web.file.payload.request.FileUploadRequest;
import com.realworld.web.file.payload.response.FileResponse;
import com.realworld.web.file.payload.response.FileResponses;
import com.realworld.web.file.payload.response.FileUrlResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Tag(
        name = "파일 관리 API",
        description = "파일 리사이즈 및 업로드, 이동, 삭제를 위한 API"
)
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileImageService fileImageService;

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
    public ResponseEntity<SuccessResponse<FileResponses>> uploadResizedImage(@ModelAttribute FileUploadRequest request) {
        List<FileResponse> fileResponses = Arrays.stream(request.getMultipartFiles())
                .map(file -> FileResponse.from(
                        fileImageService.saveResizedImage(
                                request.getDestinationDirectory(),
                                file,
                                request.getWidth(),
                                request.getHeight()
                        )
                ))
                .toList();

        FileResponses responses = FileResponses.of(fileResponses);

        SuccessResponse<FileResponses> successResponse = new SuccessResponse<>(
                responses,
                200,
                HttpStatus.OK,
                "파일 업로드 성공"
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
    public ResponseEntity<SuccessResponse<FileResponses>> uploadImage(@ModelAttribute FileUploadRequest request) {
        List<FileResponse> fileResponses = Arrays.stream(request.getMultipartFiles())
                .map(file -> FileResponse.from(
                        fileImageService.saveImage(
                                request.getDestinationDirectory(),
                                file
                        )
                ))
                .toList();

        FileResponses responses = FileResponses.of(fileResponses);

        SuccessResponse<FileResponses> successResponse = new SuccessResponse<>(
                responses,
                200,
                HttpStatus.OK,
                "파일 업로드 성공"
        );

        return ResponseEntity.ok(successResponse);
    }

    @SuccessResponseAnnotation(SuccessResponseCode.SUCCESS)
    @PatchMapping("/move")
    public ResponseEntity<SuccessResponse<FileUrlResponses>> move(@RequestBody FileMoveRequest request) {
        List<String> movedUrls = request.getUrls().stream()
                .map(url -> fileImageService.move(url, request.getDirectory()))
                .toList();

        FileUrlResponses responses = FileUrlResponses.of(movedUrls);

        SuccessResponse<FileUrlResponses> successResponse = new SuccessResponse<>(
                responses,
                200,
                HttpStatus.OK,
                "파일 이동 성공"
        );

        return ResponseEntity.ok(successResponse);
    }

    @DeleteMapping
    public ResponseEntity<SuccessResponse<FileResponse>> delete(@RequestParam("files") FileDeleteRequest request) {
        request.getUrls().forEach(fileImageService::delete);
        return ResponseEntity.ok(new SuccessResponse<>(null, 200, HttpStatus.OK, "파일 삭제 성공"));
    }

}
