package com.parfinfo.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parfinfo.dto.auth.*;
import com.parfinfo.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;
    private ChangePasswordRequest changePasswordRequest;
    private ResetPasswordRequest resetPasswordRequest;

    @BeforeEach
    void setUp() {
        // Initialisation des objets de test
        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        registerRequest = new RegisterRequest();
        registerRequest.setEmail("new@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setNom("Test");
        registerRequest.setPrenom("User");

        changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setCurrentPassword("oldPassword");
        changePasswordRequest.setNewPassword("newPassword");

        resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setEmail("test@example.com");
    }

    @Test
    void testLogin() throws Exception {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("jwt-token");
        loginResponse.setUserId(1L);

        when(authService.login(any(LoginRequest.class)))
            .thenReturn(loginResponse);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.userId").value(1L));
    }

    @Test
    void testRegister() throws Exception {
        LoginResponse registerResponse = new LoginResponse();
        registerResponse.setToken("jwt-token");
        registerResponse.setUserId(1L);

        when(authService.register(any(RegisterRequest.class)))
            .thenReturn(registerResponse);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.userId").value(1L));
    }

    @Test
    void testChangePassword() throws Exception {
        mockMvc.perform(post("/api/auth/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void testRequestPasswordReset() throws Exception {
        mockMvc.perform(post("/api/auth/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetPasswordRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void testVerifyResetToken() throws Exception {
        when(authService.verifyResetToken("valid-token"))
            .thenReturn(true);

        mockMvc.perform(get("/api/auth/reset-password/verify")
                .param("token", "valid-token"))
                .andExpect(status().isOk());
    }

    @Test
    void testSetNewPassword() throws Exception {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setToken("valid-token");
        request.setNewPassword("newPassword123");

        mockMvc.perform(post("/api/auth/reset-password/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testLogout() throws Exception {
        mockMvc.perform(post("/api/auth/logout"))
                .andExpect(status().isOk());
    }
} 