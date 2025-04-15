package com.parfinfo.controller;

import com.parfinfo.dto.role.*;
import com.parfinfo.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Rôles", description = "API de gestion des rôles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @Operation(summary = "Lister tous les rôles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des rôles récupérée avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un rôle par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rôle trouvé"),
        @ApiResponse(responseCode = "404", description = "Rôle non trouvé")
    })
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau rôle")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rôle créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody CreateRoleRequest request) {
        return ResponseEntity.ok(roleService.createRole(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un rôle")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rôle mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Rôle non trouvé")
    })
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRoleRequest request) {
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un rôle")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rôle supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Rôle non trouvé")
    })
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }
} 