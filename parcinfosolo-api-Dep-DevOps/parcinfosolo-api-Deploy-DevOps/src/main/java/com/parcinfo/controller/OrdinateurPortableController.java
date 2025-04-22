package com.parcinfo.controller;

import com.parcinfo.model.OrdinateurPortable;
import com.parcinfo.model.TypeObjetNomade;
import com.parcinfo.service.ObjetNomadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des ordinateurs portables.
 */
@RestController
@RequestMapping("/api/ordinateurs-portables")
@RequiredArgsConstructor
public class OrdinateurPortableController {

    private final ObjetNomadeService objetNomadeService;

    /**
     * Récupère tous les ordinateurs portables.
     *
     * @return la liste des ordinateurs portables
     */
    @GetMapping
    public List<OrdinateurPortable> getAllOrdinateursPortables() {
        return objetNomadeService.getObjetsNomadesByType(TypeObjetNomade.LAPTOP)
                .stream()
                .filter(obj -> obj instanceof OrdinateurPortable)
                .map(obj -> (OrdinateurPortable) obj)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un ordinateur portable par son ID.
     *
     * @param id l'ID de l'ordinateur portable
     * @return l'ordinateur portable ou 404 si non trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrdinateurPortable> getOrdinateurPortableById(@PathVariable Long id) {
        return objetNomadeService.getObjetNomadeById(id)
                .filter(obj -> obj instanceof OrdinateurPortable)
                .map(obj -> (OrdinateurPortable) obj)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crée un nouvel ordinateur portable.
     *
     * @param ordinateur l'ordinateur portable à créer
     * @return l'ordinateur portable créé
     */
    @PostMapping
    public OrdinateurPortable createOrdinateurPortable(@RequestBody OrdinateurPortable ordinateur) {
        ordinateur.setType(TypeObjetNomade.LAPTOP);
        return (OrdinateurPortable) objetNomadeService.createObjetNomade(ordinateur);
    }

    /**
     * Met à jour un ordinateur portable existant.
     *
     * @param id l'ID de l'ordinateur portable à mettre à jour
     * @param ordinateur les nouvelles données de l'ordinateur portable
     * @return l'ordinateur portable mis à jour ou 404 si non trouvé
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrdinateurPortable> updateOrdinateurPortable(
            @PathVariable Long id, @RequestBody OrdinateurPortable ordinateur) {
        try {
            ordinateur.setType(TypeObjetNomade.LAPTOP);
            OrdinateurPortable updatedOrdinateur = (OrdinateurPortable) objetNomadeService.updateObjetNomade(id, ordinateur);
            return ResponseEntity.ok(updatedOrdinateur);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Supprime un ordinateur portable.
     *
     * @param id l'ID de l'ordinateur portable à supprimer
     * @return 200 si la suppression a réussi, 404 si non trouvé
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdinateurPortable(@PathVariable Long id) {
        try {
            objetNomadeService.deleteObjetNomade(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 