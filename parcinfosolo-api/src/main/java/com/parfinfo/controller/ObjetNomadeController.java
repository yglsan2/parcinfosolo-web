package com.parfinfo.controller;

import com.parfinfo.dto.ObjetNomadeRequest;
import com.parfinfo.dto.ObjetNomadeResponse;
import com.parfinfo.service.ObjetNomadeService;
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
@RequestMapping("/api/objets-nomades")
@RequiredArgsConstructor
@Tag(name = "Objets Nomades", description = "API de gestion des objets nomades")
public class ObjetNomadeController {

    private final ObjetNomadeService objetNomadeService;

    @GetMapping
    @Operation(summary = "Liste tous les objets nomades", description = "Récupère la liste paginée des objets nomades")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des objets nomades récupérée avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Page<ObjetNomadeResponse>> getAllObjetsNomades(Pageable pageable) {
        return ResponseEntity.ok(objetNomadeService.getAllObjetsNomades(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère un objet nomade", description = "Récupère les détails d'un objet nomade par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Objet nomade trouvé"),
        @ApiResponse(responseCode = "404", description = "Objet nomade non trouvé")
    })
    public ResponseEntity<ObjetNomadeResponse> getObjetNomadeById(
            @Parameter(description = "ID de l'objet nomade") @PathVariable Long id) {
        return ResponseEntity.ok(objetNomadeService.getObjetNomadeById(id));
    }

    @PostMapping
    @Operation(summary = "Crée un objet nomade", description = "Crée un nouvel objet nomade")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Objet nomade créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<ObjetNomadeResponse> createObjetNomade(
            @Valid @RequestBody ObjetNomadeRequest request) {
        return ResponseEntity.ok(objetNomadeService.createObjetNomade(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour un objet nomade", description = "Met à jour les informations d'un objet nomade existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Objet nomade mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Objet nomade non trouvé")
    })
    public ResponseEntity<ObjetNomadeResponse> updateObjetNomade(
            @Parameter(description = "ID de l'objet nomade") @PathVariable Long id,
            @Valid @RequestBody ObjetNomadeRequest request) {
        return ResponseEntity.ok(objetNomadeService.updateObjetNomade(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime un objet nomade", description = "Supprime un objet nomade par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Objet nomade supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Objet nomade non trouvé")
    })
    public ResponseEntity<Void> deleteObjetNomade(
            @Parameter(description = "ID de l'objet nomade") @PathVariable Long id) {
        objetNomadeService.deleteObjetNomade(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Recherche des objets nomades", description = "Recherche des objets nomades selon différents critères")
    public ResponseEntity<Page<ObjetNomadeResponse>> searchObjetsNomades(
            @Parameter(description = "Type d'objet nomade") @RequestParam(required = false) String type,
            @Parameter(description = "Statut de l'objet nomade") @RequestParam(required = false) String statut,
            @Parameter(description = "Marque de l'objet nomade") @RequestParam(required = false) String marque,
            @Parameter(description = "Modèle de l'objet nomade") @RequestParam(required = false) String modele,
            Pageable pageable) {
        return ResponseEntity.ok(objetNomadeService.searchObjetsNomades(type, statut, marque, modele, pageable));
    }

    @GetMapping("/types")
    @Operation(summary = "Liste les types d'objets nomades", description = "Récupère la liste des types d'objets nomades disponibles")
    public ResponseEntity<List<String>> getAllTypes() {
        return ResponseEntity.ok(objetNomadeService.getAllTypes());
    }

    @GetMapping("/statuts")
    @Operation(summary = "Liste les statuts d'objets nomades", description = "Récupère la liste des statuts d'objets nomades disponibles")
    public ResponseEntity<List<String>> getAllStatuts() {
        return ResponseEntity.ok(objetNomadeService.getAllStatuts());
    }
} 