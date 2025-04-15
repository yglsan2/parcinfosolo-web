package com.parfinfo.controller;

import com.parfinfo.dto.objetnomade.CreateObjetNomadeRequest;
import com.parfinfo.dto.objetnomade.ObjetNomadeResponse;
import com.parfinfo.dto.objetnomade.UpdateObjetNomadeRequest;
import com.parfinfo.entity.EtatEquipement;
import com.parfinfo.entity.TypeObjetNomade;
import com.parfinfo.service.EnumerationService;
import com.parfinfo.service.ObjetNomadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/objets-nomades", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Objets Nomades", description = "API de gestion des objets nomades")
public class ObjetNomadeController {

    private final ObjetNomadeService objetNomadeService;
    private final EnumerationService enumerationService;

    @GetMapping
    @Operation(summary = "Récupérer tous les objets nomades", description = "Retourne une liste paginée des objets nomades")
    public ResponseEntity<Page<ObjetNomadeResponse>> getAllObjetsNomades(
            @Parameter(description = "Paramètres de pagination") Pageable pageable) {
        return ResponseEntity.ok(objetNomadeService.getAllObjetsNomades(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un objet nomade par son ID")
    public ResponseEntity<ObjetNomadeResponse> getObjetNomadeById(
            @Parameter(description = "ID de l'objet nomade") @PathVariable Long id) {
        return ResponseEntity.ok(objetNomadeService.getObjetNomadeById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel objet nomade")
    public ResponseEntity<ObjetNomadeResponse> createObjetNomade(
            @Valid @RequestBody CreateObjetNomadeRequest request) {
        return ResponseEntity.ok(objetNomadeService.createObjetNomade(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un objet nomade")
    public ResponseEntity<ObjetNomadeResponse> updateObjetNomade(
            @Parameter(description = "ID de l'objet nomade") @PathVariable Long id,
            @Valid @RequestBody UpdateObjetNomadeRequest request) {
        return ResponseEntity.ok(objetNomadeService.updateObjetNomade(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un objet nomade")
    public ResponseEntity<Void> deleteObjetNomade(
            @Parameter(description = "ID de l'objet nomade") @PathVariable Long id) {
        objetNomadeService.deleteObjetNomade(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des objets nomades")
    public ResponseEntity<Page<ObjetNomadeResponse>> searchObjetsNomades(
            @Parameter(description = "Type d'objet nomade") @RequestParam(required = false) TypeObjetNomade type,
            @Parameter(description = "Statut de l'objet") @RequestParam(required = false) EtatEquipement statut,
            @Parameter(description = "Marque de l'objet") @RequestParam(required = false) String marque,
            @Parameter(description = "Modèle de l'objet") @RequestParam(required = false) String modele,
            @Parameter(description = "Paramètres de pagination") Pageable pageable) {
        return ResponseEntity.ok(objetNomadeService.searchObjetsNomades(type, statut, marque, modele, pageable));
    }

    @GetMapping("/types")
    @Operation(summary = "Récupérer tous les types d'objets nomades")
    public ResponseEntity<List<String>> getTypes() {
        return ResponseEntity.ok(enumerationService.getTypes());
    }

    @GetMapping("/etats")
    @Operation(summary = "Récupérer tous les états possibles")
    public ResponseEntity<List<String>> getEtats() {
        return ResponseEntity.ok(enumerationService.getEtats());
    }

    @GetMapping("/localisations")
    @Operation(summary = "Récupérer toutes les localisations possibles")
    public ResponseEntity<List<String>> getLocalisations() {
        @GetMapping("/statuts")
        @Operation(summary = "Récupérer tous les statuts possibles")
        public ResponseEntity<List<EtatEquipement>> getAllStatuts () {
            return ResponseEntity.ok(List.of(EtatEquipement.values()));
        }
    }
}