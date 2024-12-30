package com.realworld.feature.profile.service;

import com.realworld.feature.file.domain.File;
import com.realworld.feature.member.domain.Member;
import com.realworld.feature.profile.controller.request.UpdateProfileRequest;

public interface ProfileCommandService {
    Member updateProfile(UpdateProfileRequest request, String userId);

    Member updateProfileImage(String userId, File fileId);
}
