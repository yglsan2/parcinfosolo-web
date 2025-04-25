package com.parcinfo.controller;

import com.parcinfo.model.ObjetNomade;
import com.parcinfo.model.Peripherique;
import com.parcinfo.model.TypeObjetNomade;
import com.parcinfo.service.ObjetNomadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/objets-nomades")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ObjetNomadeController {
    private final ObjetNomadeService objetNomadeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ObjetNomade> getAllObjetsNomades() {
        return objetNomadeService.getAllObjetsNomades();
    }

    @GetMapping("/{id}")
    public ObjetNomade getObjetNomadeById(@PathVariable Long id) {
        return objetNomadeService.getObjetNomadeById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Objet nomade non trouvé"));
    }

    @GetMapping("/numero-serie/{numeroSerie}")
    public ObjetNomade getObjetNomadeByNumeroSerie(@PathVariable String numeroSerie) {
        return objetNomadeService.getObjetNomadeByNumeroSerie(numeroSerie)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Objet nomade non trouvé"));
    }

    @GetMapping("/type/{type}")
    @ResponseStatus(HttpStatus.OK)
    public List<ObjetNomade> getObjetsNomadesByType(@PathVariable TypeObjetNomade type) {
        return objetNomadeService.getObjetsNomadesByType(type);
    }

    @GetMapping("/marque/{marque}")
    @ResponseStatus(HttpStatus.OK)
    public List<ObjetNomade> getObjetsNomadesByMarque(@PathVariable String marque) {
        return objetNomadeService.getObjetsNomadesByMarque(marque);
    }

    @GetMapping("/actifs")
    @ResponseStatus(HttpStatus.OK)
    public List<ObjetNomade> getObjetsNomadesActifs() {
        return objetNomadeService.getObjetsNomadesActifs();
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ObjetNomade> getObjetsNomadesByUtilisateur(@PathVariable Long utilisateurId) {
        return objetNomadeService.getObjetsNomadesByUtilisateur(utilisateurId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ObjetNomade createObjetNomade(@RequestBody ObjetNomade objetNomade) {
        try {
            return objetNomadeService.createObjetNomade(objetNomade);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ObjetNomade updateObjetNomade(@PathVariable Long id, @RequestBody ObjetNomade objetNomade) {
        try {
            return objetNomadeService.updateObjetNomade(id, objetNomade);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteObjetNomade(@PathVariable Long id) {
        try {
            objetNomadeService.deleteObjetNomade(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{id}/peripheriques")
    @ResponseStatus(HttpStatus.OK)
    public List<Peripherique> getPeripheriques(@PathVariable Long id) {
        try {
            return objetNomadeService.getPeripheriques(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/{id}/peripheriques")
    @ResponseStatus(HttpStatus.CREATED)
    public Peripherique ajouterPeripherique(@PathVariable Long id, @RequestBody Peripherique peripherique) {
        try {
            return objetNomadeService.ajouterPeripherique(id, peripherique);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
} 