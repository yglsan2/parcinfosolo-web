package com.parfinfo.controller;

import com.parfinfo.dto.ordinateur.*;
import com.parfinfo.service.OrdinateurService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordinateurs")
@RequiredArgsConstructor
@Tag(name = "Ordinateurs", description = "API de gestion des ordinateurs")
public class OrdinateurController {

    private final OrdinateurService ordinateurService;

    @GetMapping
    @Operation(summary = "Lister tous les ordinateurs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des ordinateurs récupérée avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Page<OrdinateurResponse>> getAllOrdinateurs(Pageable pageable) {
        return ResponseEntity.ok(ordinateurService.getAllOrdinateurs(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un ordinateur par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ordinateur trouvé"),
        @ApiResponse(responseCode = "404", description = "Ordinateur non trouvé")
    })
    public ResponseEntity<OrdinateurResponse> getOrdinateurById(@PathVariable Long id) {
        return ResponseEntity.ok(ordinateurService.getOrdinateurById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel ordinateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ordinateur créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<OrdinateurResponse> createOrdinateur(@Valid @RequestBody CreateOrdinateurRequest request) {
        return ResponseEntity.ok(ordinateurService.createOrdinateur(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un ordinateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ordinateur mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Ordinateur non trouvé")
    })
    public ResponseEntity<OrdinateurResponse> updateOrdinateur(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrdinateurRequest request) {
        return ResponseEntity.ok(ordinateurService.updateOrdinateur(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un ordinateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ordinateur supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Ordinateur non trouvé")
    })
    public ResponseEntity<?> deleteOrdinateur(@PathVariable Long id) {
        ordinateurService.deleteOrdinateur(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des ordinateurs")
    public ResponseEntity<List<OrdinateurResponse>> searchOrdinateurs(
            @Parameter(description = "Type d'ordinateur") @RequestParam(required = false) String type,
            @Parameter(description = "Statut de l'ordinateur") @RequestParam(required = false) String statut,
            @Parameter(description = "Marque de l'ordinateur") @RequestParam(required = false) String marque,
            @Parameter(description = "Modèle de l'ordinateur") @RequestParam(required = false) String modele) {
        return ResponseEntity.ok(ordinateurService.searchOrdinateurs(type, statut, marque, modele));
    }

    @GetMapping("/types")
    @Operation(summary = "Lister tous les types d'ordinateurs")
    public ResponseEntity<List<String>> getAllTypes() {
        return ResponseEntity.ok(ordinateurService.getAllTypes());
    }

    @GetMapping("/statuts")
    @Operation(summary = "Lister tous les statuts d'ordinateurs")
    public ResponseEntity<List<String>> getAllStatuts() {
        return ResponseEntity.ok(ordinateurService.getAllStatuts());
    }
} 