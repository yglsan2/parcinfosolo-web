package com.parfinfo.dto.peripherique;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PeripheriqueDetailResponse {
    private Long id;
    private String type;
    private String marque;
    private String modele;
    private String numeroSerie;
    private String statut;
    private String description;
    private Long appareilId;
    private String appareilNom;
    private String appareilNumeroSerie;
    private String departement;
    private String localisation;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private String createur;
    private String modificateur;
    private String historique;
    private String notes;
    private String documents;
} 