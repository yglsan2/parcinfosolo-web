package com.parfinfo.dto.ordinateur;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdateOrdinateurRequest {
    private String nom;
    private String type;
    private String marque;
    private String modele;
    private String numeroSerie;
    private String statut;
    private String localisation;
    private String description;
    private LocalDateTime dateAcquisition;
    private LocalDateTime dateDerniereMaintenance;
    private LocalDateTime dateProchaineMaintenance;
    private String fournisseur;
    
    @Positive(message = "Le coût d'acquisition doit être positif")
    private Double coutAcquisition;
    
    private String garantie;
    private String notes;
    private String processeur;
    private String ram;
    private String stockage;
    private String systemeExploitation;
    private String versionSysteme;
    private List<Long> peripheriquesIds;
} 