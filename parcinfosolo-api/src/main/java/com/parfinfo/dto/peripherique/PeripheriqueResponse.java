package com.parfinfo.dto.peripherique;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PeripheriqueResponse {
    private Long id;
    private String type;
    private String marque;
    private String modele;
    private String numeroSerie;
    private String statut;
    private String description;
    private Long appareilId;
    private String appareilNom;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
} 