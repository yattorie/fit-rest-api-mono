package com.orlovandrei.fit_rest.service;

import com.orlovandrei.fit_rest.dto.auth.LoginRequest;
import com.orlovandrei.fit_rest.dto.auth.RefreshTokenRequest;
import com.orlovandrei.fit_rest.dto.auth.RegisterRequest;
import com.orlovandrei.fit_rest.dto.auth.TokenPair;
import jakarta.transaction.Transactional;

public interface AuthService {
    @Transactional
    void register(RegisterRequest registerRequest);

    TokenPair login(LoginRequest loginRequest);

    TokenPair refreshToken(RefreshTokenRequest request);
}
