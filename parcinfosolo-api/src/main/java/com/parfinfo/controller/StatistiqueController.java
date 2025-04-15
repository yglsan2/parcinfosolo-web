package com.parfinfo.controller;

import com.parfinfo.dto.statistique.*;
import com.parfinfo.service.StatistiqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class StatistiqueController {

    private final StatistiqueService statistiqueService;

    public AppareilStatistiqueResponse getAppareilStatistiques(
            LocalDate dateDebut,
            LocalDate dateFin) {
        return statistiqueService.getAppareilStatistiques(dateDebut, dateFin);
    }

    public PeripheriqueStatistiqueResponse getPeripheriqueStatistiques(
            LocalDate dateDebut,
            LocalDate dateFin) {
        return statistiqueService.getPeripheriqueStatistiques(dateDebut, dateFin);
    }

    public UtilisateurStatistiqueResponse getUtilisateurStatistiques(
            LocalDate dateDebut,
            LocalDate dateFin) {
        return statistiqueService.getUtilisateurStatistiques(dateDebut, dateFin);
    }

    public List<ActiviteStatistiqueResponse> getActiviteStatistiques(
            LocalDate dateDebut,
            LocalDate dateFin) {
        return statistiqueService.getActiviteStatistiques(dateDebut, dateFin);
    }

    public MaintenanceStatistiqueResponse getMaintenanceStatistiques(
            LocalDate dateDebut,
            LocalDate dateFin) {
        return statistiqueService.getMaintenanceStatistiques(dateDebut, dateFin);
    }
} 