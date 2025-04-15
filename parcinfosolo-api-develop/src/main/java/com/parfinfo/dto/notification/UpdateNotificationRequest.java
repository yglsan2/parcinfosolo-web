package com.parfinfo.dto.notification;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNotificationRequest {
    @NotBlank(message = "Le type est obligatoire")
    private String type;

    @NotBlank(message = "Le message est obligatoire")
    private String message;

    @NotBlank(message = "Le destinataire est obligatoire")
    private String destinataire;

    private boolean lu;
} 