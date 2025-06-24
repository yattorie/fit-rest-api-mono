package com.orlovandrei.fit_rest.service;

import com.orlovandrei.fit_rest.dto.user.UpdateUserProfileRequest;
import com.orlovandrei.fit_rest.dto.user.UserProfileDto;

public interface UserProfileService {
    UserProfileDto getProfile(String username);
    UserProfileDto updateProfile(UpdateUserProfileRequest request, String username);

    int calculateCalorieNorm(String username);
}

