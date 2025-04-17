package com.parcinfo.controller;

import com.parcinfo.api.response.ApiResponse;
import com.parcinfo.model.ObjetNomade;
import com.parcinfo.model.Peripherique;
import com.parcinfo.model.TypeObjetNomade;
import com.parcinfo.service.ObjetNomadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objets-nomades")
@RequiredArgsConstructor
public class ObjetNomadeController {
    private final ObjetNomadeService objetNomadeService;

    @GetMapping
    public List<ObjetNomade> getAllObjetsNomades() {
        return objetNomadeService.getAllObjetsNomades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjetNomade> getObjetNomadeById(@PathVariable Long id) {
        return objetNomadeService.getObjetNomadeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero-serie/{numeroSerie}")
    public ResponseEntity<ObjetNomade> getObjetNomadeByNumeroSerie(@PathVariable String numeroSerie) {
        return objetNomadeService.getObjetNomadeByNumeroSerie(numeroSerie)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public List<ObjetNomade> getObjetsNomadesByType(@PathVariable TypeObjetNomade type) {
        return objetNomadeService.getObjetsNomadesByType(type);
    }

    @GetMapping("/marque/{marque}")
    public List<ObjetNomade> getObjetsNomadesByMarque(@PathVariable String marque) {
        return objetNomadeService.getObjetsNomadesByMarque(marque);
    }

    @GetMapping("/actifs")
    public List<ObjetNomade> getObjetsNomadesActifs() {
        return objetNomadeService.getObjetsNomadesActifs();
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public List<ObjetNomade> getObjetsNomadesByUtilisateur(@PathVariable Long utilisateurId) {
        return objetNomadeService.getObjetsNomadesByUtilisateur(utilisateurId);
    }

    @PostMapping
    public ObjetNomade createObjetNomade(@RequestBody ObjetNomade objetNomade) {
        return objetNomadeService.createObjetNomade(objetNomade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjetNomade> updateObjetNomade(@PathVariable Long id, @RequestBody ObjetNomade objetNomade) {
        try {
            ObjetNomade updatedObjetNomade = objetNomadeService.updateObjetNomade(id, objetNomade);
            return ResponseEntity.ok(updatedObjetNomade);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObjetNomade(@PathVariable Long id) {
        try {
            objetNomadeService.deleteObjetNomade(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/peripheriques")
    public ResponseEntity<ApiResponse<List<Peripherique>>> getPeripheriques(@PathVariable Long id) {
        try {
            List<Peripherique> peripheriques = objetNomadeService.getPeripheriques(id);
            return ResponseEntity.ok(ApiResponse.success(peripheriques));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/{id}/peripheriques")
    public ResponseEntity<ApiResponse<Peripherique>> ajouterPeripherique(@PathVariable Long id, @RequestBody Peripherique peripherique) {
        try {
            Peripherique nouveauPeripherique = objetNomadeService.ajouterPeripherique(id, peripherique);
            return ResponseEntity.ok(ApiResponse.success("Périphérique ajouté avec succès", nouveauPeripherique));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(e.getMessage()));
        }
    }
} 