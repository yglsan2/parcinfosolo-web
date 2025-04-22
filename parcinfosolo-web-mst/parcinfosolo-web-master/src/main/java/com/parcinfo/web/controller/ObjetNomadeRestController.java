package com.parcinfo.web.controller;

import com.parcinfo.web.model.ObjetNomade;
import com.parcinfo.web.service.ObjetNomadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objets-nomades")
public class ObjetNomadeRestController {

    @Autowired
    private ObjetNomadeService objetNomadeService;

    @GetMapping
    public List<ObjetNomade> getAllObjetsNomades() {
        return objetNomadeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjetNomade> getObjetNomadeById(@PathVariable Long id) {
        ObjetNomade objetNomade = objetNomadeService.findById(id);
        if (objetNomade != null) {
            return ResponseEntity.ok(objetNomade);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ObjetNomade> createObjetNomade(@RequestBody ObjetNomade objetNomade) {
        try {
            ObjetNomade savedObjetNomade = objetNomadeService.save(objetNomade);
            return ResponseEntity.ok(savedObjetNomade);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjetNomade> updateObjetNomade(@PathVariable Long id, @RequestBody ObjetNomade objetNomade) {
        try {
            objetNomade.setIdObjetNomade(id);
            ObjetNomade updatedObjetNomade = objetNomadeService.save(objetNomade);
            return ResponseEntity.ok(updatedObjetNomade);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObjetNomade(@PathVariable Long id) {
        try {
            objetNomadeService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 