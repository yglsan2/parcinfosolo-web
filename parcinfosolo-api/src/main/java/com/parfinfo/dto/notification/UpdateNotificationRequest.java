package com.parfinfo.dto.notification;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateNotificationRequest {
    @NotBlank(message = "Le titre est requis")
    private String titre;

    @NotBlank(message = "Le message est requis")
    private String message;

    @NotBlank(message = "Le type est requis")
    private String type;

    @NotBlank(message = "La priorité est requise")
    private String priorite;

    private Boolean lu;
} 