package com.parfinfo.dto.peripherique;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreatePeripheriqueRequest {
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
    private Long ordinateurId;
} 