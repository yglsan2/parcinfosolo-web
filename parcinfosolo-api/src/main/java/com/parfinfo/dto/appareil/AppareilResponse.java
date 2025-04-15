package com.parfinfo.dto.appareil;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppareilResponse {
    private Long id;
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
    private Double coutAcquisition;
    private String garantie;
    private String notes;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
} 