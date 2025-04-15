package com.parfinfo.controller;

import com.parfinfo.dto.auth.*;
import com.parfinfo.service.AuthService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    public LoginResponse login(LoginRequest request) {
        return authService.login(request);
    }

    public RegisterResponse register(RegisterRequest request) {
        return authService.register(request);
    }

    public UserResponse getCurrentUser() {
        return authService.getCurrentUser();
    }

    public UserResponse updateProfile(UpdateProfileRequest request) {
        return authService.updateProfile(request);
    }

    public void changePassword(ChangePasswordRequest request) {
        authService.changePassword(request);
    }

    public void resetPassword(String email) {
        authService.resetPassword(email);
    }

    public void verifyResetToken(String token) {
        authService.verifyResetToken(token);
    }

    public void setNewPassword(String token, String newPassword) {
        authService.setNewPassword(token, newPassword);
    }
} 