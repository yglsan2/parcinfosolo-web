package com.parfinfo.dto.statistique;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatistiqueRequest {
    @NotBlank(message = "Le type est obligatoire")
    private String type;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotNull(message = "Les données sont obligatoires")
    private Map<String, Object> donnees;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDateTime dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDateTime dateFin;

    @NotBlank(message = "La période est obligatoire")
    private String periode;
} 