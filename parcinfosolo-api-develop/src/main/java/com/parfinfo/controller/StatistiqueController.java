package com.parfinfo.controller;

import com.parfinfo.dto.statistique.*;
import com.parfinfo.service.StatistiqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/statistiques")
@RequiredArgsConstructor
@Tag(name = "Statistiques", description = "API de gestion des statistiques")
public class StatistiqueController {

    private final StatistiqueService statistiqueService;

    @GetMapping("/appareils")
    @Operation(summary = "Obtenir les statistiques des appareils")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statistiques récupérées avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<AppareilStatistiqueResponse> getAppareilStatistiques(
            @Parameter(description = "Date de début") 
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @Parameter(description = "Date de fin") 
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return ResponseEntity.ok(statistiqueService.getAppareilStatistiques(dateDebut, dateFin));
    }

    @GetMapping("/peripheriques")
    @Operation(summary = "Obtenir les statistiques des périphériques")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statistiques récupérées avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<PeripheriqueStatistiqueResponse> getPeripheriqueStatistiques(
            @Parameter(description = "Date de début") 
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @Parameter(description = "Date de fin") 
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return ResponseEntity.ok(statistiqueService.getPeripheriqueStatistiques(dateDebut, dateFin));
    }

    @GetMapping("/utilisateurs")
    @Operation(summary = "Obtenir les statistiques des utilisateurs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statistiques récupérées avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<UtilisateurStatistiqueResponse> getUtilisateurStatistiques(
            @Parameter(description = "Date de début") 
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @Parameter(description = "Date de fin") 
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return ResponseEntity.ok(statistiqueService.getUtilisateurStatistiques(dateDebut, dateFin));
    }

    @GetMapping("/activites")
    @Operation(summary = "Obtenir les statistiques des activités")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statistiques récupérées avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<List<ActiviteStatistiqueResponse>> getActiviteStatistiques(
            @Parameter(description = "Date de début") 
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @Parameter(description = "Date de fin") 
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return ResponseEntity.ok(statistiqueService.getActiviteStatistiques(dateDebut, dateFin));
    }

    @GetMapping("/maintenances")
    @Operation(summary = "Obtenir les statistiques des maintenances")
    public ResponseEntity<MaintenanceStatistiqueResponse> getMaintenanceStatistiques(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return ResponseEntity.ok(statistiqueService.getMaintenanceStatistiques(dateDebut, dateFin));
    }
} 