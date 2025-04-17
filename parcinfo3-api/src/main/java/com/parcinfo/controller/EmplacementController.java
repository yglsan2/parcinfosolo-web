package com.parcinfo.controller;

import com.parcinfo.model.Emplacement;
import com.parcinfo.service.EmplacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emplacements")
public class EmplacementController {
    
    @Autowired
    private EmplacementService emplacementService;
    
    @GetMapping
    public List<Emplacement> getAllEmplacements() {
        return emplacementService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Emplacement> getEmplacementById(@PathVariable Long id) {
        return emplacementService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/parc/{parcId}")
    public List<Emplacement> getEmplacementsByParc(@PathVariable Long parcId) {
        return emplacementService.findByParcId(parcId);
    }
    
    @GetMapping("/type/{typeEmplacementId}")
    public List<Emplacement> getEmplacementsByType(@PathVariable Long typeEmplacementId) {
        return emplacementService.findByTypeEmplacementId(typeEmplacementId);
    }
    
    @PostMapping
    public Emplacement createEmplacement(@RequestBody Emplacement emplacement) {
        return emplacementService.save(emplacement);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Emplacement> updateEmplacement(@PathVariable Long id, @RequestBody Emplacement emplacement) {
        return emplacementService.findById(id)
            .map(existingEmplacement -> {
                emplacement.setIdEmplacement(id);
                return ResponseEntity.ok(emplacementService.save(emplacement));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmplacement(@PathVariable Long id) {
        return emplacementService.findById(id)
            .map(emplacement -> {
                emplacementService.deleteById(id);
                return ResponseEntity.ok().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
} 