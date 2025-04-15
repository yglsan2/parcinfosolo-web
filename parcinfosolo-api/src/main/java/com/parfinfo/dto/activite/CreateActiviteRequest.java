package com.parfinfo.dto.activite;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateActiviteRequest {
    @NotBlank(message = "Le type est requis")
    private String type;
    
    @NotBlank(message = "La description est requise")
    private String description;
    
    @NotNull(message = "L'ID de l'utilisateur est requis")
    private Long utilisateurId;
    
    private Long appareilId;
} 