package com.parfinfo.dto.peripherique;

import lombok.Data;
import java.util.Map;

@Data
public class PeripheriqueStatistiqueResponse {
    private long totalPeripheriques;
    private Map<String, Long> countByType;
    private Map<String, Long> countByStatut;
    private Map<String, Long> countByMarque;
    private Map<String, Long> countByAppareil;
    private double tauxUtilisation;
    private double tauxDefectueux;
} 