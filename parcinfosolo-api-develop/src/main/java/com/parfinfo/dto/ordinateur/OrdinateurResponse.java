package com.parfinfo.dto.ordinateur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdinateurResponse {
    private Long id;
    private String type;
    private String marque;
    private String modele;
    private String numeroSerie;
    private String statut;
    private String processeur;
    private String ram;
    private String stockage;
    private String systemeExploitation;
    private String versionOS;
    private String description;
    private LocalDateTime dateAcquisition;
    private LocalDateTime dateDerniereMaintenance;
    private LocalDateTime dateProchaineMaintenance;
    private String localisation;
    private String utilisateur;
    private String fournisseur;
    private Double coutAcquisition;
    private String garantie;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 