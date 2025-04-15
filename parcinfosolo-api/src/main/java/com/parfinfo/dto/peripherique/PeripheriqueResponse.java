package com.parfinfo.dto.peripherique;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PeripheriqueResponse {
    private Long id;
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
    private Double coutAcquisition;
    private String garantie;
    private String notes;
    private Long ordinateurId;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
} 