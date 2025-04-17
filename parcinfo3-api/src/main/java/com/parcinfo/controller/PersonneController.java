package com.parcinfo.controller;

import com.parcinfo.api.response.ApiResponse;
import com.parcinfo.model.Personne;
import com.parcinfo.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnes")
public class PersonneController {
    
    @Autowired
    private PersonneService personneService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Personne>>> getAllPersonnes() {
        List<Personne> personnes = personneService.findAll();
        return ResponseEntity.ok(ApiResponse.success(personnes));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Personne>> getPersonneById(@PathVariable Long id) {
        return personneService.findById(id)
                .map(personne -> ResponseEntity.ok(ApiResponse.success(personne)))
                .orElse(ResponseEntity.ok(ApiResponse.error("Personne non trouvée")));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Personne>> createPersonne(@RequestBody Personne personne) {
        Personne savedPersonne = personneService.save(personne);
        return ResponseEntity.ok(ApiResponse.success("Personne créée avec succès", savedPersonne));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Personne>> updatePersonne(@PathVariable Long id, @RequestBody Personne personne) {
        if (!personneService.existsById(id)) {
            return ResponseEntity.ok(ApiResponse.error("Personne non trouvée"));
        }
        personne.setIdPersonne(id);
        Personne updatedPersonne = personneService.save(personne);
        return ResponseEntity.ok(ApiResponse.success("Personne mise à jour avec succès", updatedPersonne));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePersonne(@PathVariable Long id) {
        if (!personneService.existsById(id)) {
            return ResponseEntity.ok(ApiResponse.error("Personne non trouvée"));
        }
        personneService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Personne supprimée avec succès", null));
    }
} 