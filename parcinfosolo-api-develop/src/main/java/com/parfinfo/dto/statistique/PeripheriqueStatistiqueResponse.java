package com.parfinfo.dto.statistique;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeripheriqueStatistiqueResponse {
    private long totalPeripheriques;
    private long peripheriquesEnService;
    private long peripheriquesEnPanne;
    private long peripheriquesEnMaintenance;
    private long peripheriquesRetires;
    private long nouveauxPeripheriques;
    private long peripheriquesParType;
    private long peripheriquesParMarque;
    private long peripheriquesParStatut;
} 