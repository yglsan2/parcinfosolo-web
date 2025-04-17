package com.parcinfo.controller;

import com.parcinfo.model.Peripherique;
import com.parcinfo.model.TypePeripherique;
import com.parcinfo.service.PeripheriqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peripheriques")
@RequiredArgsConstructor
public class PeripheriqueController {
    private final PeripheriqueService peripheriqueService;

    @GetMapping
    public List<Peripherique> getAllPeripheriques() {
        return peripheriqueService.getAllPeripheriques();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peripherique> getPeripheriqueById(@PathVariable Long id) {
        return peripheriqueService.getPeripheriqueById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero-serie/{numeroSerie}")
    public ResponseEntity<Peripherique> getPeripheriqueByNumeroSerie(@PathVariable String numeroSerie) {
        return peripheriqueService.getPeripheriqueByNumeroSerie(numeroSerie)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public List<Peripherique> getPeripheriquesByType(@PathVariable TypePeripherique type) {
        return peripheriqueService.getPeripheriquesByType(type);
    }

    @GetMapping("/marque/{marque}")
    public List<Peripherique> getPeripheriquesByMarque(@PathVariable String marque) {
        return peripheriqueService.getPeripheriquesByMarque(marque);
    }

    @GetMapping("/actifs")
    public List<Peripherique> getPeripheriquesActifs() {
        return peripheriqueService.getPeripheriquesActifs();
    }

    @GetMapping("/objet-nomade/{idObjetNomade}")
    public List<Peripherique> getPeripheriquesByObjetNomade(@PathVariable Long idObjetNomade) {
        return peripheriqueService.getPeripheriquesByObjetNomade(idObjetNomade);
    }

    @GetMapping("/ordinateur/{idOrdinateur}")
    public List<Peripherique> getPeripheriquesByOrdinateur(@PathVariable Long idOrdinateur) {
        return peripheriqueService.getPeripheriquesByOrdinateur(idOrdinateur);
    }

    @PostMapping
    public Peripherique createPeripherique(@RequestBody Peripherique peripherique) {
        return peripheriqueService.createPeripherique(peripherique);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Peripherique> updatePeripherique(@PathVariable Long id, @RequestBody Peripherique peripherique) {
        try {
            Peripherique updatedPeripherique = peripheriqueService.updatePeripherique(id, peripherique);
            return ResponseEntity.ok(updatedPeripherique);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeripherique(@PathVariable Long id) {
        try {
            peripheriqueService.deletePeripherique(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 