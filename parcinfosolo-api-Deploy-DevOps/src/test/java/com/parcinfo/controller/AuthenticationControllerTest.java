package com.parcinfo.controller;

import com.parcinfo.dto.RegisterRequest;
import com.parcinfo.model.Role;
import com.parcinfo.model.User;
import com.parcinfo.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    private User testUser;
    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        registerRequest = RegisterRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        testUser = User.builder()
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();
    }

    @Test
    void loginPage_ShouldReturnLoginView() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }

    @Test
    void registerPage_ShouldReturnRegisterView() throws Exception {
        mockMvc.perform(get("/auth/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"))
                .andExpect(model().attributeExists("registerRequest"));
    }

    @Test
    void register_WhenSuccessful_ShouldRedirectToLogin() throws Exception {
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(testUser);

        mockMvc.perform(post("/auth/register")
                        .param("username", registerRequest.getUsername())
                        .param("email", registerRequest.getEmail())
                        .param("password", registerRequest.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/login"))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    void register_WhenError_ShouldRedirectToRegister() throws Exception {
        when(authenticationService.register(any(RegisterRequest.class)))
                .thenThrow(new RuntimeException("Test error"));

        mockMvc.perform(post("/auth/register")
                        .param("username", registerRequest.getUsername())
                        .param("email", registerRequest.getEmail())
                        .param("password", registerRequest.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/register"))
                .andExpect(flash().attributeExists("error"));
    }
} 