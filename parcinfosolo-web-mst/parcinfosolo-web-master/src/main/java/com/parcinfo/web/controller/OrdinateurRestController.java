package com.parcinfo.web.controller;

import com.parcinfo.web.model.Ordinateur;
import com.parcinfo.web.service.OrdinateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordinateurs")
public class OrdinateurRestController {

    @Autowired
    private OrdinateurService ordinateurService;

    @GetMapping
    public List<Ordinateur> getAllOrdinateurs() {
        return ordinateurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ordinateur> getOrdinateurById(@PathVariable Long id) {
        Ordinateur ordinateur = ordinateurService.findById(id);
        if (ordinateur != null) {
            return ResponseEntity.ok(ordinateur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Ordinateur> createOrdinateur(@RequestBody Ordinateur ordinateur) {
        try {
            Ordinateur savedOrdinateur = ordinateurService.save(ordinateur);
            return ResponseEntity.ok(savedOrdinateur);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ordinateur> updateOrdinateur(@PathVariable Long id, @RequestBody Ordinateur ordinateur) {
        try {
            ordinateur.setIdAppareil(id);
            Ordinateur updatedOrdinateur = ordinateurService.save(ordinateur);
            return ResponseEntity.ok(updatedOrdinateur);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdinateur(@PathVariable Long id) {
        try {
            ordinateurService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 