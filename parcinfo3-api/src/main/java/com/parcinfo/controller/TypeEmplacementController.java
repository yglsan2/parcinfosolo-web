package com.parcinfo.controller;

import com.parcinfo.model.TypeEmplacement;
import com.parcinfo.service.TypeEmplacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types-emplacement")
public class TypeEmplacementController {
    
    @Autowired
    private TypeEmplacementService typeEmplacementService;
    
    @GetMapping
    public List<TypeEmplacement> getAllTypesEmplacement() {
        return typeEmplacementService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TypeEmplacement> getTypeEmplacementById(@PathVariable Long id) {
        return typeEmplacementService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public TypeEmplacement createTypeEmplacement(@RequestBody TypeEmplacement typeEmplacement) {
        return typeEmplacementService.save(typeEmplacement);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TypeEmplacement> updateTypeEmplacement(@PathVariable Long id, @RequestBody TypeEmplacement typeEmplacement) {
        return typeEmplacementService.findById(id)
            .map(existingTypeEmplacement -> {
                typeEmplacement.setIdTypeEmplacement(id);
                return ResponseEntity.ok(typeEmplacementService.save(typeEmplacement));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeEmplacement(@PathVariable Long id) {
        return typeEmplacementService.findById(id)
            .map(typeEmplacement -> {
                typeEmplacementService.deleteById(id);
                return ResponseEntity.ok().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
} 