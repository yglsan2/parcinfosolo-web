package com.parfinfo.controller;

import com.parfinfo.dto.appareil.*;
import com.parfinfo.service.AppareilService;
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
@CrossOrigin(origins = "*", maxAge = 3600)
public class AppareilController {

    private final AppareilService appareilService;

    @GetMapping
    public ResponseEntity<Page<AppareilResponse>> getAllAppareils(Pageable pageable) {
        return ResponseEntity.ok(appareilService.getAllAppareils(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppareilResponse> getAppareilById(@PathVariable Long id) {
        return ResponseEntity.ok(appareilService.getAppareilById(id));
    }

    @PostMapping
    public ResponseEntity<AppareilResponse> createAppareil(@Valid @RequestBody CreateAppareilRequest request) {
        return ResponseEntity.ok(appareilService.createAppareil(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppareilResponse> updateAppareil(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAppareilRequest request) {
        return ResponseEntity.ok(appareilService.updateAppareil(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppareil(@PathVariable Long id) {
        appareilService.deleteAppareil(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<AppareilResponse>> searchAppareils(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(appareilService.searchAppareils(type, status));
    }

    @GetMapping("/types")
    public ResponseEntity<List<String>> getAllTypes() {
        return ResponseEntity.ok(appareilService.getAllTypes());
    }

    @GetMapping("/status")
    public ResponseEntity<List<String>> getAllStatus() {
        return ResponseEntity.ok(appareilService.getAllStatus());
    }
} 