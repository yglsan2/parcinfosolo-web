package com.parfinfo.dto.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateNotificationRequest {
    @NotBlank(message = "Le titre est requis")
    private String titre;

    @NotBlank(message = "Le message est requis")
    private String message;

    @NotBlank(message = "Le type est requis")
    private String type;

    @NotBlank(message = "La priorité est requise")
    private String priorite;

    @NotNull(message = "L'ID du destinataire est requis")
    private Long destinataireId;
} 