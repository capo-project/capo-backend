package com.realworld.feature.file.controller;

import com.realworld.feature.file.controller.Response.GetFileResponse;
import com.realworld.feature.file.domain.File;
import com.realworld.feature.file.service.StorageService;
import com.realworld.global.code.SuccessCode;
import com.realworld.global.response.ApiResponse;
import com.realworld.global.utils.FileUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final StorageService cloudStorageService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<List<FileResponse>>> uploadFiles(@AuthenticationPrincipal User user, @RequestParam(name = "file") MultipartFile[] multipartFiles) throws IOException {

        List<FileResponse> fileResponseList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {


            File file = FileUtil.fileSetting(multipartFile);

            try (InputStream inputStream = multipartFile.getInputStream()) {
                File savedFile = cloudStorageService.upload(inputStream, user.getUsername(), file);
                fileResponseList.add(savedFile.toResponse());
            }
        }

        ApiResponse<List<FileResponse>> fileUploadResponse = new ApiResponse<>(fileResponseList,
                SuccessCode.INSERT_SUCCESS.getStatus(), SuccessCode.INSERT_SUCCESS.getMessage());

        return ResponseEntity.status(HttpStatus.CREATED).body(fileUploadResponse);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<ApiResponse<GetFileResponse>> getFile(@AuthenticationPrincipal User user,
                                                                @PathVariable String fileId,
                                                                HttpServletResponse response) throws IOException {

        String url = cloudStorageService.getFile(fileId);
        GetFileResponse fileResponse = GetFileResponse.builder()
                .imageUrl(url)
                .build();

        ApiResponse<GetFileResponse> apiResponse = new ApiResponse<>(fileResponse, SuccessCode.SELECT_SUCCESS.getStatus(), SuccessCode.SELECT_SUCCESS.getMessage());

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<ApiResponse<?>> deleteFile(@AuthenticationPrincipal User user, @PathVariable String fileId) {
        cloudStorageService.delete(user.getUsername(), fileId);

        ApiResponse<?> fileDeleteResponse = new ApiResponse<>(null,
                SuccessCode.DELETE_SUCCESS.getStatus(), SuccessCode.DELETE_SUCCESS.getMessage());

        return ResponseEntity.ok(fileDeleteResponse);
    }
}
