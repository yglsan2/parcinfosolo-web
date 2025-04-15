package com.parfinfo.dto.ordinateur;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrdinateurResponse {
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
    private String processeur;
    private String ram;
    private String stockage;
    private String systemeExploitation;
    private String versionSysteme;
    private List<Long> peripheriquesIds;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
} 