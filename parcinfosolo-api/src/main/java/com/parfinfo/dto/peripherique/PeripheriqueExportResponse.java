package com.parfinfo.dto.peripherique;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PeripheriqueExportResponse {
    private Long id;
    private String type;
    private String marque;
    private String modele;
    private String numeroSerie;
    private String statut;
    private String description;
    private String appareilNom;
    private String appareilNumeroSerie;
    private String departement;
    private String localisation;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private String historique;
} 