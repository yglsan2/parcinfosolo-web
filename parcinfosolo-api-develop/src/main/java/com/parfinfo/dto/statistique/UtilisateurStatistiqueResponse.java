package com.parfinfo.dto.statistique;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurStatistiqueResponse {
    private long totalUtilisateurs;
    private long utilisateursActifs;
    private long utilisateursInactifs;
    private long nouveauxUtilisateurs;
    private long utilisateursParRole;
    private long utilisateursParDepartement;
    private long utilisateursParStatut;
} 