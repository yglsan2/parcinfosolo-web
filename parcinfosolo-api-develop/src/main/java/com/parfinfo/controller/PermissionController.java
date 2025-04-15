package com.parfinfo.controller;

import com.parfinfo.dto.PermissionDTO;
import com.parfinfo.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@Tag(name = "Permissions", description = "API de gestion des permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    @Operation(summary = "Récupérer toutes les permissions")
    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
        return ResponseEntity.ok(permissionService.getAllPermissions());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une permission par son ID")
    public ResponseEntity<PermissionDTO> getPermissionById(@PathVariable Long id) {
        return permissionService.getPermissionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Récupérer une permission par son nom")
    public ResponseEntity<PermissionDTO> getPermissionByName(@PathVariable String name) {
        return permissionService.getPermissionByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle permission")
    public ResponseEntity<PermissionDTO> createPermission(@Valid @RequestBody PermissionDTO permissionDTO) {
        return ResponseEntity.ok(permissionService.createPermission(permissionDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une permission")
    public ResponseEntity<PermissionDTO> updatePermission(
            @PathVariable Long id,
            @Valid @RequestBody PermissionDTO permissionDTO) {
        return permissionService.updatePermission(id, permissionDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une permission")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok().build();
    }
} 