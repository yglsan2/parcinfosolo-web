package com.parfinfo.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private String[] roles;
} 