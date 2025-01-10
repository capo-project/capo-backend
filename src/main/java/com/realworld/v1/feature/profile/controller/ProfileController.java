package com.realworld.v1.feature.profile.controller;

import com.realworld.feature.profile.Response.UpdateProfileImageResponse;
import com.realworld.feature.profile.Response.UpdateProfileResponse;
import com.realworld.feature.profile.controller.request.UpdateProfileRequest;
import com.realworld.v1.feature.file.domain.FileV1;
import com.realworld.v1.feature.file.service.StorageService;
import com.realworld.v1.feature.member.domain.Member;
import com.realworld.v1.feature.profile.service.ProfileCommandService;
import com.realworld.v1.global.code.SuccessCode;
import com.realworld.v1.global.response.ApiResponse;
import com.realworld.v1.global.utils.FileUtil;
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
@RequestMapping("/v1/member")
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

        FileV1 fileV1 = FileUtil.fileSetting(multipartFile);

        FileV1 savedFileV1 = null;
        try (InputStream inputStream = multipartFile.getInputStream()) {
            savedFileV1 = cloudStorageService.upload(inputStream, user.getUsername(), fileV1);

        }

        Member member = profileCommandService.updateProfileImage(user.getUsername(), savedFileV1);

        UpdateProfileImageResponse response = UpdateProfileImageResponse.builder()
                .fileId(member.getFileV1().getId())
                .build();

        ApiResponse<UpdateProfileImageResponse> apiResponse = new ApiResponse<>(response, SuccessCode.UPDATE_SUCCESS.getStatus(), SuccessCode.UPDATE_SUCCESS.getMessage());

        return ResponseEntity.ok(apiResponse);
    }
}
