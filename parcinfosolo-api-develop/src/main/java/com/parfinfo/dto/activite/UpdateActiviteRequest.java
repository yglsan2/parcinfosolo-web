package com.parfinfo.dto.activite;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateActiviteRequest {
    @NotBlank(message = "Le type est obligatoire")
    private String type;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotBlank(message = "L'utilisateur est obligatoire")
    private String utilisateur;

    @NotBlank(message = "L'entité est obligatoire")
    private String entite;

    @NotNull(message = "L'ID de l'entité est obligatoire")
    private Long entiteId;

    @NotNull(message = "La date d'activité est obligatoire")
    private LocalDateTime dateActivite;

    private String details;
} 