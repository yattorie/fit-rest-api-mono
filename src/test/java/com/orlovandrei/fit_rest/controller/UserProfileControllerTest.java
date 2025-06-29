package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.user.UpdateUserProfileRequest;
import com.orlovandrei.fit_rest.dto.user.UserProfileDto;
import com.orlovandrei.fit_rest.service.UserProfileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class UserProfileControllerTest {

    @Mock
    private UserProfileService userProfileService;

    @InjectMocks
    private UserProfileController userProfileController;

    @Mock
    private UserDetails userDetails;

    @Test
    void getProfile_success() {
        UserProfileDto dto = new UserProfileDto();
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(userProfileService.getProfile("user")).thenReturn(dto);

        ResponseEntity<UserProfileDto> result = userProfileController.getProfile(userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void updateProfile_success() {
        UpdateUserProfileRequest req = new UpdateUserProfileRequest();
        UserProfileDto dto = new UserProfileDto();
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(userProfileService.updateProfile(req, "user")).thenReturn(dto);

        ResponseEntity<UserProfileDto> result = userProfileController.updateProfile(req, userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(dto, result.getBody());
    }

    @Test
    void getCalorieNorm_success() {
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(userProfileService.calculateCalorieNorm("user")).thenReturn(2150);

        ResponseEntity<Integer> result = userProfileController.getCalorieNorm(userDetails);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(2150, result.getBody());
    }
}