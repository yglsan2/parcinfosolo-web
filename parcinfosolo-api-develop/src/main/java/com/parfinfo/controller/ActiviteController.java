package com.parfinfo.controller;

import com.parfinfo.dto.activite.*;
import com.parfinfo.service.ActiviteService;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/activites")
@RequiredArgsConstructor
@Tag(name = "Activités", description = "API de gestion des activités")
public class ActiviteController {

    private final ActiviteService activiteService;

    @GetMapping
    @Operation(summary = "Lister toutes les activités")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des activités récupérée avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Page<ActiviteResponse>> getAllActivites(Pageable pageable) {
        return ResponseEntity.ok(activiteService.getAllActivites(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une activité par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Activité trouvée"),
        @ApiResponse(responseCode = "404", description = "Activité non trouvée")
    })
    public ResponseEntity<ActiviteResponse> getActiviteById(@PathVariable Long id) {
        return ResponseEntity.ok(activiteService.getActiviteById(id));
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle activité")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Activité créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<ActiviteResponse> createActivite(@Valid @RequestBody CreateActiviteRequest request) {
        return ResponseEntity.ok(activiteService.createActivite(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une activité")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Activité mise à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Activité non trouvée")
    })
    public ResponseEntity<ActiviteResponse> updateActivite(
            @PathVariable Long id,
            @Valid @RequestBody UpdateActiviteRequest request) {
        return ResponseEntity.ok(activiteService.updateActivite(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une activité")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Activité supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Activité non trouvée")
    })
    public ResponseEntity<?> deleteActivite(@PathVariable Long id) {
        activiteService.deleteActivite(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des activités")
    public ResponseEntity<List<ActiviteResponse>> searchActivites(
            @Parameter(description = "Type d'activité") @RequestParam(required = false) String type,
            @Parameter(description = "Date de début") 
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @Parameter(description = "Date de fin") 
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @Parameter(description = "ID de l'utilisateur") @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(activiteService.searchActivites(type, dateDebut, dateFin, userId));
    }

    @GetMapping("/types")
    @Operation(summary = "Lister tous les types d'activités")
    public ResponseEntity<List<String>> getAllTypes() {
        return ResponseEntity.ok(activiteService.getAllTypes());
    }
} 