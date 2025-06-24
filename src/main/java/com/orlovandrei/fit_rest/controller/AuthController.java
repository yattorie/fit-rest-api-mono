package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.auth.LoginRequest;
import com.orlovandrei.fit_rest.dto.auth.RefreshTokenRequest;
import com.orlovandrei.fit_rest.dto.auth.RegisterRequest;
import com.orlovandrei.fit_rest.dto.auth.TokenPair;
import com.orlovandrei.fit_rest.dto.success.SuccessResponse;
import com.orlovandrei.fit_rest.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController{

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(
            @Valid
            @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse("Registration successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid
            @RequestBody LoginRequest loginRequest) {
        TokenPair tokenPair = authService.login(loginRequest);
        return ResponseEntity.ok(tokenPair);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(
            @Valid
            @RequestBody RefreshTokenRequest request) {
        TokenPair tokenPair = authService.refreshToken(request);
        return ResponseEntity.ok(tokenPair);
    }
}
