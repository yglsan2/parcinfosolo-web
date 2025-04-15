package com.parfinfo.controller;

import com.parfinfo.dto.peripherique.PeripheriqueResponse;
import com.parfinfo.dto.peripherique.CreatePeripheriqueRequest;
import com.parfinfo.dto.peripherique.UpdatePeripheriqueRequest;
import com.parfinfo.entity.Peripherique;
import com.parfinfo.entity.EtatEquipement;
import com.parfinfo.service.PeripheriqueService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/peripheriques", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PeripheriqueController {

    private final PeripheriqueService peripheriqueService;

    @GetMapping
    public ResponseEntity<Page<PeripheriqueResponse>> getAllPeripheriques(Pageable pageable) {
        return ResponseEntity.ok(peripheriqueService.getAllPeripheriques(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeripheriqueResponse> getPeripheriqueById(@PathVariable Long id) {
        return ResponseEntity.ok(peripheriqueService.getPeripheriqueById(id));
    }

    @PostMapping
    public ResponseEntity<PeripheriqueResponse> createPeripherique(
            @Valid @RequestBody CreatePeripheriqueRequest request) {
        return ResponseEntity.ok(peripheriqueService.createPeripherique(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeripheriqueResponse> updatePeripherique(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePeripheriqueRequest request) {
        return ResponseEntity.ok(peripheriqueService.updatePeripherique(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePeripherique(@PathVariable Long id) {
        peripheriqueService.deletePeripherique(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<PeripheriqueResponse>> searchPeripheriques(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) String marque,
            @RequestParam(required = false) String modele) {
        return ResponseEntity.ok(peripheriqueService.searchPeripheriques(type, statut, marque, modele));
    }

    @GetMapping("/types")
    public ResponseEntity<List<String>> getAllTypes() {
        return ResponseEntity.ok(peripheriqueService.getAllTypes());
    }

    @GetMapping("/statuts")
    public ResponseEntity<List<String>> getAllStatuts() {
        return ResponseEntity.ok(peripheriqueService.getAllStatuts());
    }

    @GetMapping("/ordinateur/{ordinateurId}")
    public List<Peripherique> getPeripheriquesByOrdinateur(@PathVariable Long ordinateurId) {
        return peripheriqueService.getPeripheriquesByOrdinateur(ordinateurId);
    }

    @GetMapping("/etat/{etat}")
    public List<Peripherique> getPeripheriquesByEtat(@PathVariable EtatEquipement etat) {
        return peripheriqueService.getPeripheriquesByEtat(etat);
    }
} 