package com.realworld.v1.feature.profile.service;

import com.realworld.feature.profile.controller.request.UpdateProfileRequest;
import com.realworld.v1.feature.file.domain.FileV1;
import com.realworld.v1.feature.member.domain.Member;

public interface ProfileCommandService {
    Member updateProfile(UpdateProfileRequest request, String userId);

    Member updateProfileImage(String userId, FileV1 fileV1Id);
}
