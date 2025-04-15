package com.parfinfo.dto.statistique;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppareilStatistiqueResponse {
    private long totalAppareils;
    private long appareilsEnService;
    private long appareilsEnPanne;
    private long appareilsEnMaintenance;
    private long appareilsRetires;
    private long nouveauxAppareils;
    private long appareilsRetires;
    private long appareilsEnMaintenance;
    private long appareilsEnPanne;
    private long appareilsEnService;
    private long totalAppareils;
} 