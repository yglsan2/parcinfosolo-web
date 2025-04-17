package com.parcinfo.controller;

import com.parcinfo.model.Parc;
import com.parcinfo.service.ParcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parcs")
public class ParcController {
    
    @Autowired
    private ParcService parcService;
    
    @GetMapping
    public List<Parc> getAllParcs() {
        return parcService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Parc> getParcById(@PathVariable Long id) {
        return parcService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Parc createParc(@RequestBody Parc parc) {
        return parcService.save(parc);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Parc> updateParc(@PathVariable Long id, @RequestBody Parc parc) {
        return parcService.findById(id)
            .map(existingParc -> {
                parc.setIdParc(id);
                return ResponseEntity.ok(parcService.save(parc));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParc(@PathVariable Long id) {
        return parcService.findById(id)
            .map(parc -> {
                parcService.deleteById(id);
                return ResponseEntity.ok().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
} 