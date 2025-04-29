package com.parcinfo.web.service;

import com.parcinfo.web.model.Peripherique;
import com.parcinfo.web.repository.PeripheriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des périphériques.
 * Implémente la logique métier liée aux opérations CRUD sur les périphériques.
 *
 * @author [Votre nom]
 * @version 1.0
 */
@Service
public class PeripheriqueService {

    private final PeripheriqueRepository peripheriqueRepository;

    @Autowired
    public PeripheriqueService(PeripheriqueRepository peripheriqueRepository) {
        this.peripheriqueRepository = peripheriqueRepository;
    }

    /**
     * Récupère tous les périphériques.
     *
     * @return La liste de tous les périphériques
     */
    public List<Peripherique> findAll() {
        return peripheriqueRepository.findAll();
    }

    /**
     * Récupère un périphérique par son identifiant.
     *
     * @param id L'identifiant du périphérique
     * @return Le périphérique trouvé ou une exception si non trouvé
     */
    public Peripherique findById(Long id) {
        return peripheriqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Périphérique non trouvé avec l'id : " + id));
    }

    /**
     * Sauvegarde un périphérique.
     *
     * @param peripherique Le périphérique à sauvegarder
     * @return Le périphérique sauvegardé
     */
    public Peripherique save(Peripherique peripherique) {
        return peripheriqueRepository.save(peripherique);
    }

    /**
     * Met à jour un périphérique existant.
     *
     * @param id          L'identifiant du périphérique à mettre à jour
     * @param peripherique Les nouvelles données du périphérique
     * @return Le périphérique mis à jour
     * @throws RuntimeException si le périphérique n'existe pas
     */
    public Peripherique update(Long id, Peripherique peripherique) {
        if (!peripheriqueRepository.existsById(id)) {
            throw new RuntimeException("Périphérique non trouvé avec l'id : " + id);
        }
        peripherique.setId(id);
        return peripheriqueRepository.save(peripherique);
    }

    /**
     * Supprime un périphérique par son identifiant.
     *
     * @param id L'identifiant du périphérique à supprimer
     */
    public void deleteById(Long id) {
        peripheriqueRepository.deleteById(id);
    }
} 