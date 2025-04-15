package com.parfinfo.service;

import com.parfinfo.entity.Activite;
import com.parfinfo.entity.Utilisateur;
import com.parfinfo.entity.Appareil;
import com.parfinfo.exception.ResourceNotFoundException;
import com.parfinfo.repository.ActiviteRepository;
import com.parfinfo.repository.UtilisateurRepository;
import com.parfinfo.repository.AppareilRepository;
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

    @Autowired
    private AppareilRepository appareilRepository;

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

    public Page<Activite> getActivitesByUser(Long userId, Pageable pageable) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", userId));
        return activiteRepository.findByUtilisateur(utilisateur, pageable);
    }

    public Page<Activite> getActivitesByAppareil(Long appareilId, Pageable pageable) {
        Appareil appareil = appareilRepository.findById(appareilId)
                .orElseThrow(() -> new ResourceNotFoundException("Appareil", "id", appareilId));
        return activiteRepository.findByAppareil(appareil, pageable);
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

    public Activite updateActivite(Long id, Activite activiteDetails) {
        Activite activite = getActiviteById(id);
        activite.setType(activiteDetails.getType());
        activite.setDescription(activiteDetails.getDescription());
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