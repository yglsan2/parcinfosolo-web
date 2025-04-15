package com.parfinfo.dto.objetnomade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjetNomadeResponse {
    private Long id;
    private String type;
    private String marque;
    private String modele;
    private String numeroSerie;
    private String statut;
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