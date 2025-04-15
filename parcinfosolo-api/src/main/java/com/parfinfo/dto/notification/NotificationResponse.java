package com.parfinfo.dto.notification;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Long id;
    private String titre;
    private String message;
    private String type;
    private String priorite;
    private Boolean lu;
    private Long destinataireId;
    private String destinataireNom;
    private LocalDateTime dateCreation;
    private LocalDateTime dateLecture;
} 