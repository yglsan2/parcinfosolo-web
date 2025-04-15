package com.parfinfo.dto.peripherique;

import com.parfinfo.entity.EtatEquipement;
import com.parfinfo.entity.TypePeripherique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UpdatePeripheriqueRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "La marque est obligatoire")
    private String marque;

    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;

    @NotBlank(message = "Le numéro de série est obligatoire")
    private String numeroSerie;

    @NotNull(message = "Le type est obligatoire")
    private TypePeripherique type;

    @NotNull(message = "L'état est obligatoire")
    private EtatEquipement etat;

    private String description;

    @NotBlank(message = "La localisation est obligatoire")
    private String localisation;

    private LocalDate dateAcquisition;
    private LocalDate derniereMaintenance;
    private Long ordinateurId;
    private String emplacement;
    private String fabricant;
    private String versionFirmware;
    private String versionDriver;

    private String statut;
    private LocalDateTime dateDerniereMaintenance;
    private LocalDateTime dateProchaineMaintenance;
    private String fournisseur;
    
    @Positive(message = "Le coût d'acquisition doit être positif")
    private Double coutAcquisition;
    
    private String garantie;
    private String notes;
} 