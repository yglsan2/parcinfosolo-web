package com.parfinfo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ObjetNomadeRequest {
    @NotBlank(message = "Le type est obligatoire")
    private String type;

    @NotBlank(message = "Le statut est obligatoire")
    private String statut;

    @NotBlank(message = "La marque est obligatoire")
    private String marque;

    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;

    private String numeroSerie;
    private String commentaire;
    
    @NotNull(message = "L'ID de l'utilisateur est obligatoire")
    private Long utilisateurId;
} 