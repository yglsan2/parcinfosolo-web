package com.parcinfo.controller;

import com.parcinfo.model.Affectation;
import com.parcinfo.service.AffectationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/affectations")
public class AffectationController {
    
    @Autowired
    private AffectationService affectationService;
    
    @GetMapping
    public List<Affectation> getAllAffectations() {
        return affectationService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Affectation> getAffectationById(@PathVariable Long id) {
        return affectationService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/personne/{personneId}")
    public List<Affectation> getAffectationsByPersonne(@PathVariable Long personneId) {
        return affectationService.findByPersonneId(personneId);
    }
    
    @GetMapping("/appareil/{appareilId}")
    public List<Affectation> getAffectationsByAppareil(@PathVariable Long appareilId) {
        return affectationService.findByAppareilId(appareilId);
    }
    
    @PostMapping
    public Affectation createAffectation(@RequestParam Long personneId, @RequestParam Long appareilId) {
        return affectationService.createAffectation(personneId, appareilId);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAffectation(@PathVariable Long id) {
        affectationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 