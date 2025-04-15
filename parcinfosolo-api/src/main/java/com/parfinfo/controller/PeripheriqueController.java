package com.parfinfo.controller;

import com.parfinfo.dto.peripherique.*;
import com.parfinfo.model.Peripherique;
import com.parfinfo.service.PeripheriqueService;
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
@RequestMapping("/api/peripheriques")
@RequiredArgsConstructor
@Tag(name = "Périphériques", description = "API de gestion des périphériques")
public class PeripheriqueController {

    private final PeripheriqueService peripheriqueService;

    @GetMapping
    @Operation(summary = "Lister tous les périphériques")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des périphériques récupérée avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Page<PeripheriqueResponse>> getAllPeripheriques(Pageable pageable) {
        return ResponseEntity.ok(peripheriqueService.getAllPeripheriques(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un périphérique par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Périphérique trouvé"),
        @ApiResponse(responseCode = "404", description = "Périphérique non trouvé")
    })
    public ResponseEntity<PeripheriqueResponse> getPeripheriqueById(@PathVariable Long id) {
        return ResponseEntity.ok(peripheriqueService.getPeripheriqueById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau périphérique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Périphérique créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<PeripheriqueResponse> createPeripherique(
            @Valid @RequestBody CreatePeripheriqueRequest request) {
        return ResponseEntity.ok(peripheriqueService.createPeripherique(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un périphérique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Périphérique mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Périphérique non trouvé")
    })
    public ResponseEntity<PeripheriqueResponse> updatePeripherique(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePeripheriqueRequest request) {
        return ResponseEntity.ok(peripheriqueService.updatePeripherique(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un périphérique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Périphérique supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Périphérique non trouvé")
    })
    public ResponseEntity<?> deletePeripherique(@PathVariable Long id) {
        peripheriqueService.deletePeripherique(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des périphériques")
    public ResponseEntity<List<PeripheriqueResponse>> searchPeripheriques(
            @Parameter(description = "Type de périphérique") @RequestParam(required = false) String type,
            @Parameter(description = "Statut du périphérique") @RequestParam(required = false) String statut,
            @Parameter(description = "Marque du périphérique") @RequestParam(required = false) String marque,
            @Parameter(description = "Modèle du périphérique") @RequestParam(required = false) String modele) {
        return ResponseEntity.ok(peripheriqueService.searchPeripheriques(type, statut, marque, modele));
    }

    @GetMapping("/types")
    @Operation(summary = "Lister tous les types de périphériques")
    public ResponseEntity<List<String>> getAllTypes() {
        return ResponseEntity.ok(peripheriqueService.getAllTypes());
    }

    @GetMapping("/statuts")
    @Operation(summary = "Lister tous les statuts de périphériques")
    public ResponseEntity<List<String>> getAllStatuts() {
        return ResponseEntity.ok(peripheriqueService.getAllStatuts());
    }
} 