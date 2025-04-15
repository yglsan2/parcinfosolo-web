package com.parfinfo.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Long id;
    private String type;
    private String message;
    private String destinataire;
    private boolean lu;
    private LocalDateTime dateEnvoi;
    private LocalDateTime dateLecture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 