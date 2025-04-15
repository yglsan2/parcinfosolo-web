package com.parfinfo.dto.peripherique;

import lombok.Data;

@Data
public class PeripheriqueSearchRequest {
    private String type;
    private String statut;
    private String marque;
    private String modele;
    private Long appareilId;
    private String numeroSerie;
    private String description;
} 