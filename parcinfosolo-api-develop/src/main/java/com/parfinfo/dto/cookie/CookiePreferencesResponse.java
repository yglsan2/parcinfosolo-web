package com.parfinfo.dto.cookie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CookiePreferencesResponse {
    private Long id;
    private String utilisateur;
    private boolean analyticsEnabled;
    private boolean marketingEnabled;
    private boolean necessaryEnabled;
    private LocalDateTime lastUpdate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 