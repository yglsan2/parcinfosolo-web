package com.parfinfo.service;

import com.parfinfo.entity.Activite;
import com.parfinfo.entity.Utilisateur;
import com.parfinfo.exception.ResourceNotFoundException;
import com.parfinfo.repository.ActiviteRepository;
import com.parfinfo.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ActiviteService {

    @Autowired
    private ActiviteRepository activiteRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Activite> getAllActivites() {
        return activiteRepository.findAll();
    }

    public Page<Activite> getAllActivites(Pageable pageable) {
        return activiteRepository.findAll(pageable);
    }

    public Activite getActiviteById(Long id) {
        return activiteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activité", "id", id));
    }

    public List<Activite> getActivitesByUtilisateur(Long utilisateurId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", utilisateurId));
        return activiteRepository.findByUtilisateur(utilisateur);
    }

    public List<Activite> getActivitesByType(String type) {
        return activiteRepository.findByType(type);
    }

    public List<Activite> getActivitesByPeriode(LocalDateTime debut, LocalDateTime fin) {
        return activiteRepository.findByDateCreationBetween(debut, fin);
    }

    public Activite createActivite(Activite activite) {
        activite.setDateCreation(LocalDateTime.now());
        return activiteRepository.save(activite);
    }

    public void deleteActivite(Long id) {
        if (!activiteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Activité", "id", id);
        }
        activiteRepository.deleteById(id);
    }

    public List<String> getAllTypes() {
        return activiteRepository.findAllTypes();
    }

    public Page<Activite> searchActivites(String type, Long utilisateurId, LocalDateTime debut, LocalDateTime fin, Pageable pageable) {
        return activiteRepository.searchActivites(type, utilisateurId, debut, fin, pageable);
    }
} 