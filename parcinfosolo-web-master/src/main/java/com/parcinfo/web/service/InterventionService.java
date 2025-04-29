package com.parcinfo.web.service;

import com.parcinfo.web.model.Intervention;
import com.parcinfo.web.repository.InterventionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service gérant les opérations métier liées aux interventions.
 * Ce service fait le lien entre les contrôleurs et le repository des interventions.
 *
 * @author [Votre nom]
 * @version 1.0
 */
@Service
@Transactional
public class InterventionService {

    @Autowired
    private InterventionRepository interventionRepository;

    /**
     * Récupère toutes les interventions.
     *
     * @return la liste de toutes les interventions
     */
    public List<Intervention> findAll() {
        return interventionRepository.findAll();
    }

    /**
     * Récupère une intervention par son ID.
     *
     * @param id l'identifiant de l'intervention
     * @return l'intervention trouvée ou une exception si non trouvée
     */
    public Intervention findById(Long id) {
        return interventionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervention non trouvée avec l'id : " + id));
    }

    /**
     * Sauvegarde une intervention.
     * Si l'intervention a un ID, elle sera mise à jour, sinon une nouvelle sera créée.
     *
     * @param intervention l'intervention à sauvegarder
     * @return l'intervention sauvegardée
     */
    public Intervention save(Intervention intervention) {
        return interventionRepository.save(intervention);
    }

    /**
     * Supprime une intervention.
     */
    public void delete(Intervention intervention) {
        interventionRepository.delete(intervention);
    }

    /**
     * Récupère les interventions d'un périphérique.
     */
    public List<Intervention> findByPeripheriqueId(Long peripheriqueId) {
        return interventionRepository.findByPeripheriqueIdOrderByDateDesc(peripheriqueId);
    }

    /**
     * Met à jour une intervention existante.
     *
     * @param id l'identifiant de l'intervention à mettre à jour
     * @param intervention les nouvelles données de l'intervention
     * @return l'intervention mise à jour
     */
    public Intervention update(Long id, Intervention intervention) {
        if (!interventionRepository.existsById(id)) {
            throw new RuntimeException("Intervention non trouvée avec l'id : " + id);
        }
        intervention.setId(id);
        return interventionRepository.save(intervention);
    }

    /**
     * Supprime une intervention par son ID.
     */
    public void deleteById(Long id) {
        interventionRepository.deleteById(id);
    }
} 