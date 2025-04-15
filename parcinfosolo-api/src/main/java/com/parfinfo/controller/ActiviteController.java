package com.parfinfo.controller;

import com.parfinfo.dto.activite.*;
import com.parfinfo.entity.Activite;
import com.parfinfo.mapper.ActiviteMapper;
import com.parfinfo.service.ActiviteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/activites")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ActiviteController {

    private final ActiviteService activiteService;
    private final ActiviteMapper activiteMapper;

    @GetMapping
    public ResponseEntity<Page<ActiviteResponse>> getAllActivites(Pageable pageable) {
        Page<Activite> activites = activiteService.getAllActivites(pageable);
        return ResponseEntity.ok(activites.map(activiteMapper::toResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActiviteResponse> getActiviteById(@PathVariable Long id) {
        Activite activite = activiteService.getActiviteById(id);
        return ResponseEntity.ok(activiteMapper.toResponse(activite));
    }

    @PostMapping
    public ResponseEntity<ActiviteResponse> createActivite(@Valid @RequestBody CreateActiviteRequest request) {
        Activite activite = activiteMapper.toEntity(request);
        Activite savedActivite = activiteService.createActivite(activite);
        return ResponseEntity.ok(activiteMapper.toResponse(savedActivite));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActiviteResponse> updateActivite(
            @PathVariable Long id,
            @Valid @RequestBody UpdateActiviteRequest request) {
        Activite activite = activiteMapper.toEntity(request);
        activite.setId(id);
        Activite updatedActivite = activiteService.updateActivite(id, activite);
        return ResponseEntity.ok(activiteMapper.toResponse(updatedActivite));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActivite(@PathVariable Long id) {
        activiteService.deleteActivite(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ActiviteResponse>> searchActivites(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin,
            Pageable pageable) {
        Page<Activite> activites = activiteService.searchActivites(type, null, dateDebut, dateFin, pageable);
        return ResponseEntity.ok(activites.map(activiteMapper::toResponse));
    }

    @GetMapping("/appareil/{appareilId}")
    public ResponseEntity<Page<ActiviteResponse>> getActivitesByAppareil(
            @PathVariable Long appareilId,
            Pageable pageable) {
        Page<Activite> activites = activiteService.getActivitesByAppareil(appareilId, pageable);
        return ResponseEntity.ok(activites.map(activiteMapper::toResponse));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ActiviteResponse>> getActivitesByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        Page<Activite> activites = activiteService.getActivitesByUser(userId, pageable);
        return ResponseEntity.ok(activites.map(activiteMapper::toResponse));
    }
} 