package com.realworld.feature.profile.controller;

import com.realworld.feature.file.domain.File;
import com.realworld.feature.file.service.StorageService;
import com.realworld.feature.member.domain.Member;
import com.realworld.feature.profile.Response.UpdateProfileImageResponse;
import com.realworld.feature.profile.Response.UpdateProfileResponse;
import com.realworld.feature.profile.controller.request.UpdateProfileRequest;
import com.realworld.feature.profile.service.ProfileCommandService;
import com.realworld.global.code.SuccessCode;
import com.realworld.global.response.ApiResponse;
import com.realworld.global.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
    private final ProfileCommandService profileCommandService;
    private final StorageService cloudStorageService;

    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateProfileResponse>> profileUpdate(@AuthenticationPrincipal User user, @RequestBody UpdateProfileRequest request) {
        Member member = profileCommandService.updateProfile(request, user.getUsername());

        UpdateProfileResponse response = UpdateProfileResponse.builder()
                .nickname(member.getNickname())
                .content(member.getContent())
                .build();

        ApiResponse<UpdateProfileResponse> updateApiResponse = new ApiResponse<>(response,
                SuccessCode.UPDATE_SUCCESS.getStatus(),
                SuccessCode.UPDATE_SUCCESS.getMessage());

        return ResponseEntity.ok(updateApiResponse);
    }

    @PatchMapping("/file")
    public ResponseEntity<ApiResponse<UpdateProfileImageResponse>> profileImageUpdate(@AuthenticationPrincipal User user, MultipartFile multipartFile) throws IOException {

        File file = FileUtil.fileSetting(multipartFile);

        File savedFile = null;
        try (InputStream inputStream = multipartFile.getInputStream()) {
            savedFile = cloudStorageService.upload(inputStream, user.getUsername(), file);

        }

        Member member = profileCommandService.updateProfileImage(user.getUsername(), savedFile);

        UpdateProfileImageResponse response = UpdateProfileImageResponse.builder()
                .fileId(member.getFile().getId())
                .build();

        ApiResponse<UpdateProfileImageResponse> apiResponse = new ApiResponse<>(response, SuccessCode.UPDATE_SUCCESS.getStatus(), SuccessCode.UPDATE_SUCCESS.getMessage());

        return ResponseEntity.ok(apiResponse);
    }
}
