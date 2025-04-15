package com.parfinfo.dto.appareil;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UpdateAppareilRequest {
    private String nom;
    private String type;
    private String marque;
    private String modele;
    private String numeroSerie;
    private String status;
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
} 