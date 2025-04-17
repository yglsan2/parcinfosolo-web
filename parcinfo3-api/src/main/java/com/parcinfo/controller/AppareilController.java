package com.parcinfo.controller;

import com.parcinfo.model.Appareil;
import com.parcinfo.model.Ordinateur;
import com.parcinfo.model.ObjetNomade;
import com.parcinfo.service.AppareilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appareils")
public class AppareilController {
    
    @Autowired
    private AppareilService appareilService;
    
    @GetMapping
    public List<Appareil> getAllAppareils() {
        return appareilService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Appareil> getAppareilById(@PathVariable Long id) {
        return appareilService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/ordinateurs")
    public List<Ordinateur> getAllOrdinateurs() {
        return appareilService.findAllOrdinateurs();
    }
    
    @GetMapping("/ordinateurs/{id}")
    public ResponseEntity<Ordinateur> getOrdinateurById(@PathVariable Long id) {
        return appareilService.findOrdinateurById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/ordinateurs")
    public Ordinateur createOrdinateur(@RequestBody Ordinateur ordinateur) {
        return appareilService.saveOrdinateur(ordinateur);
    }
    
    @GetMapping("/objets-nomades")
    public List<ObjetNomade> getAllObjetsNomades() {
        return appareilService.findAllObjetsNomades();
    }
    
    @GetMapping("/objets-nomades/{id}")
    public ResponseEntity<ObjetNomade> getObjetNomadeById(@PathVariable Long id) {
        return appareilService.findObjetNomadeById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/objets-nomades")
    public ObjetNomade createObjetNomade(@RequestBody ObjetNomade objetNomade) {
        return appareilService.saveObjetNomade(objetNomade);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Appareil> updateAppareil(@PathVariable Long id, @RequestBody Appareil appareil) {
        if (!appareilService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        appareil.setIdAppareil(id);
        return ResponseEntity.ok(appareilService.save(appareil));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppareil(@PathVariable Long id) {
        if (!appareilService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        appareilService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 