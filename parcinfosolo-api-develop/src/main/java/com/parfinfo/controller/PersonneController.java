package com.parfinfo.controller;

import com.parfinfo.dto.PersonneDTO;
import com.parfinfo.model.Personne;
import com.parfinfo.service.PersonneService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<PersonneDTO>> getAllPersonnes() {
        return ResponseEntity.ok(personneService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonneDTO> getPersonne(@PathVariable Long id) {
        return ResponseEntity.ok(personneService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PersonneDTO> createPersonne(@Valid @RequestBody PersonneDTO personneDTO) {
        return ResponseEntity.ok(personneService.save(personneDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonneDTO> updatePersonne(@PathVariable Long id, @Valid @RequestBody PersonneDTO personneDTO) {
        return ResponseEntity.ok(personneService.update(id, personneDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        personneService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<PersonneDTO>> searchPersonnes(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String prenom,
            @RequestParam(required = false) String email) {
        return ResponseEntity.ok(personneService.search(nom, prenom, email));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countPersonnes() {
        return ResponseEntity.ok(personneService.count());
    }
} 