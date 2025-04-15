package com.parfinfo.dto.personne;

import lombok.Data;
import java.util.Map;

@Data
public class PersonneStatistiqueResponse {
    private long totalPersonnes;
    private Map<String, Long> countByDepartement;
    private Map<String, Long> countByRole;
    private Map<String, Long> countByStatut;
    private Map<String, Long> countByLocalisation;
    private double tauxActif;
    private double tauxInactif;
} 