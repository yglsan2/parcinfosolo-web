package com.parfinfo.controller;

import com.parfinfo.dto.auth.*;
import com.parfinfo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API d'authentification")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Connexion utilisateur")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    @Operation(summary = "Inscription utilisateur")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/me")
    @Operation(summary = "Obtenir l'utilisateur courant")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }

    @PutMapping("/profile")
    @Operation(summary = "Mettre à jour le profil")
    public ResponseEntity<UserResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(authService.updateProfile(request));
    }

    @PutMapping("/password")
    @Operation(summary = "Changer le mot de passe")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/reset")
    @Operation(summary = "Demander la réinitialisation du mot de passe")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/password/reset/{token}")
    @Operation(summary = "Vérifier un token de réinitialisation")
    public ResponseEntity<?> verifyResetToken(@PathVariable String token) {
        authService.verifyResetToken(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/reset/{token}")
    @Operation(summary = "Définir un nouveau mot de passe")
    public ResponseEntity<?> setNewPassword(
            @PathVariable String token,
            @Valid @RequestBody NewPasswordRequest request) {
        authService.setNewPassword(token, request.getNewPassword());
        return ResponseEntity.ok().build();
    }
} 