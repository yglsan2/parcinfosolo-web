package com.parcinfo.controller;

import com.parcinfo.model.Ordinateur;
import com.parcinfo.service.AppareilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordinateurs")
public class OrdinateurController {
    
    @Autowired
    private AppareilService appareilService;
    
    @GetMapping
    public List<Ordinateur> getAllOrdinateurs() {
        return appareilService.findAllOrdinateurs();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Ordinateur> getOrdinateurById(@PathVariable Long id) {
        return appareilService.findOrdinateurById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Ordinateur createOrdinateur(@RequestBody Ordinateur ordinateur) {
        return appareilService.saveOrdinateur(ordinateur);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Ordinateur> updateOrdinateur(@PathVariable Long id, @RequestBody Ordinateur ordinateur) {
        return appareilService.findOrdinateurById(id)
            .map(existingOrdinateur -> {
                ordinateur.setIdAppareil(id);
                return ResponseEntity.ok(appareilService.saveOrdinateur(ordinateur));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdinateur(@PathVariable Long id) {
        return appareilService.findOrdinateurById(id)
            .map(ordinateur -> {
                appareilService.deleteById(id);
                return ResponseEntity.ok().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
} 