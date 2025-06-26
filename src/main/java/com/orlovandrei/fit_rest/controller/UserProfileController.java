package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.user.UpdateUserProfileRequest;
import com.orlovandrei.fit_rest.dto.user.UserProfileDto;
import com.orlovandrei.fit_rest.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
@Tag(name = "User Profile Controller", description = "Operations for managing user profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get user profile")
    public ResponseEntity<UserProfileDto> getProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                userProfileService.getProfile(userDetails.getUsername())
        );
    }

    @PutMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update user profile")
    public ResponseEntity<UserProfileDto> updateProfile(
            @RequestBody @Valid UpdateUserProfileRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                userProfileService.updateProfile(request, userDetails.getUsername())
        );
    }

    @GetMapping("/calories")
    @Operation(summary = "Calculate daily calorie norm")
    public ResponseEntity<Integer> getCalorieNorm(@AuthenticationPrincipal UserDetails userDetails) {
        int calories = userProfileService.calculateCalorieNorm(userDetails.getUsername());
        return ResponseEntity.ok(calories);
    }
}

