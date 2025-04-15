package com.parfinfo.controller;

import com.parfinfo.dto.appareil.*;
import com.parfinfo.service.AppareilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appareils")
@RequiredArgsConstructor
@Tag(name = "Appareils", description = "API de gestion des appareils")
public class AppareilController {

    private final AppareilService appareilService;

    @GetMapping
    @Operation(summary = "Lister tous les appareils")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des appareils récupérée avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Page<AppareilResponse>> getAllAppareils(Pageable pageable) {
        return ResponseEntity.ok(appareilService.getAllAppareils(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un appareil par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Appareil trouvé"),
        @ApiResponse(responseCode = "404", description = "Appareil non trouvé")
    })
    public ResponseEntity<AppareilResponse> getAppareilById(@PathVariable Long id) {
        return ResponseEntity.ok(appareilService.getAppareilById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel appareil")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Appareil créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<AppareilResponse> createAppareil(
            @Valid @RequestBody CreateAppareilRequest request) {
        return ResponseEntity.ok(appareilService.createAppareil(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un appareil")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Appareil mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Appareil non trouvé")
    })
    public ResponseEntity<AppareilResponse> updateAppareil(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAppareilRequest request) {
        return ResponseEntity.ok(appareilService.updateAppareil(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un appareil")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Appareil supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Appareil non trouvé")
    })
    public ResponseEntity<?> deleteAppareil(@PathVariable Long id) {
        appareilService.deleteAppareil(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des appareils")
    public ResponseEntity<List<AppareilResponse>> searchAppareils(
            @Parameter(description = "Type d'appareil") @RequestParam(required = false) String type,
            @Parameter(description = "Statut de l'appareil") @RequestParam(required = false) String statut,
            @Parameter(description = "Marque de l'appareil") @RequestParam(required = false) String marque,
            @Parameter(description = "Modèle de l'appareil") @RequestParam(required = false) String modele) {
        return ResponseEntity.ok(appareilService.searchAppareils(type, statut, marque, modele));
    }

    @GetMapping("/types")
    @Operation(summary = "Lister tous les types d'appareils")
    public ResponseEntity<List<String>> getAllTypes() {
        return ResponseEntity.ok(appareilService.getAllTypes());
    }

    @GetMapping("/statuts")
    @Operation(summary = "Lister tous les statuts d'appareils")
    public ResponseEntity<List<String>> getAllStatuts() {
        return ResponseEntity.ok(appareilService.getAllStatuts());
    }
} 