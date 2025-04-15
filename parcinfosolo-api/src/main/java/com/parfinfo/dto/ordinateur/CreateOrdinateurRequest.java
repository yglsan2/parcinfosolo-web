package com.parfinfo.dto.ordinateur;

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
public class CreateOrdinateurRequest {
    @NotBlank(message = "Le type est obligatoire")
    private String type;

    @NotBlank(message = "La marque est obligatoire")
    private String marque;

    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;

    @NotBlank(message = "Le numéro de série est obligatoire")
    private String numeroSerie;

    @NotBlank(message = "Le statut est obligatoire")
    private String statut;

    @NotBlank(message = "Le processeur est obligatoire")
    private String processeur;

    @NotBlank(message = "La RAM est obligatoire")
    private String ram;

    @NotBlank(message = "Le stockage est obligatoire")
    private String stockage;

    @NotBlank(message = "Le système d'exploitation est obligatoire")
    private String systemeExploitation;

    @NotBlank(message = "La version du système d'exploitation est obligatoire")
    private String versionOS;

    private String description;

    @NotNull(message = "La date d'acquisition est obligatoire")
    private LocalDateTime dateAcquisition;

    private LocalDateTime dateDerniereMaintenance;
    private LocalDateTime dateProchaineMaintenance;
    private String localisation;
    private String utilisateur;
    private String fournisseur;
    private Double coutAcquisition;
    private String garantie;
    private String notes;
} 