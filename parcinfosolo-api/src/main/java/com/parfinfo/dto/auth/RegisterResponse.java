package com.parfinfo.dto.auth;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class RegisterResponse {
    private Long id;
    private String username;
    private String email;
    private String token;
} 