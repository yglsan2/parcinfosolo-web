package com.parfinfo.dto.ordinateur;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateOrdinateurRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

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

    private String localisation;
    private String description;

    @NotNull(message = "La date d'acquisition est obligatoire")
    private LocalDateTime dateAcquisition;

    private LocalDateTime dateDerniereMaintenance;
    private LocalDateTime dateProchaineMaintenance;

    @NotBlank(message = "Le fournisseur est obligatoire")
    private String fournisseur;

    @NotNull(message = "Le coût d'acquisition est obligatoire")
    @Positive(message = "Le coût d'acquisition doit être positif")
    private Double coutAcquisition;

    private String garantie;
    private String notes;

    @NotBlank(message = "Le processeur est obligatoire")
    private String processeur;

    @NotBlank(message = "La RAM est obligatoire")
    private String ram;

    @NotBlank(message = "Le stockage est obligatoire")
    private String stockage;

    @NotBlank(message = "Le système d'exploitation est obligatoire")
    private String systemeExploitation;

    @NotBlank(message = "La version du système est obligatoire")
    private String versionSysteme;

    private List<Long> peripheriquesIds;
} 