package com.parcinfo.controller;

import com.parcinfo.api.response.ApiResponse;
import com.parcinfo.model.Personne;
import com.parcinfo.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personnes")
public class PersonneController {
    
    @Autowired
    private PersonneService personneService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Personne>>> getAllPersonnes() {
        List<Personne> personnes = personneService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Liste des personnes récupérée avec succès", personnes));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Personne>> getPersonneById(@PathVariable Long id) {
        Optional<Personne> personne = personneService.findById(id);
        if (personne.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Personne trouvée", personne.get()));
        }
        return ResponseEntity.ok(new ApiResponse<>(false, "Personne non trouvée", null));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Personne>> createPersonne(@RequestBody Personne personne) {
        Personne savedPersonne = personneService.save(personne);
        return ResponseEntity.ok(new ApiResponse<>(true, "Personne créée avec succès", savedPersonne));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Personne>> updatePersonne(@PathVariable Long id, @RequestBody Personne personne) {
        if (!personneService.existsById(id)) {
            return ResponseEntity.ok(new ApiResponse<>(false, "Personne non trouvée", null));
        }
        personne.setIdPersonne(id);
        Personne updatedPersonne = personneService.save(personne);
        return ResponseEntity.ok(new ApiResponse<>(true, "Personne mise à jour avec succès", updatedPersonne));
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