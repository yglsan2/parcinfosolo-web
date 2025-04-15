package com.parfinfo.service;

import com.parfinfo.entity.Ordinateur;
import com.parfinfo.entity.Utilisateur;
import com.parfinfo.exception.ResourceNotFoundException;
import com.parfinfo.repository.OrdinateurRepository;
import com.parfinfo.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrdinateurService {

    @Autowired
    private OrdinateurRepository ordinateurRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Ordinateur> getAllOrdinateurs() {
        return ordinateurRepository.findAll();
    }

    public Page<Ordinateur> getAllOrdinateurs(Pageable pageable) {
        return ordinateurRepository.findAll(pageable);
    }

    public Ordinateur getOrdinateurById(Long id) {
        return ordinateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordinateur", "id", id));
    }

    public List<Ordinateur> getOrdinateursByType(String type) {
        return ordinateurRepository.findByType(type);
    }

    public List<Ordinateur> getOrdinateursByStatut(String statut) {
        return ordinateurRepository.findByStatut(statut);
    }

    public List<Ordinateur> getOrdinateursByUtilisateur(Long utilisateurId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", utilisateurId));
        return ordinateurRepository.findByUtilisateur(utilisateur);
    }

    public Ordinateur createOrdinateur(Ordinateur ordinateur) {
        ordinateur.setDateCreation(LocalDateTime.now());
        return ordinateurRepository.save(ordinateur);
    }

    public Ordinateur updateOrdinateur(Long id, Ordinateur ordinateurDetails) {
        Ordinateur ordinateur = getOrdinateurById(id);
        
        ordinateur.setType(ordinateurDetails.getType());
        ordinateur.setStatut(ordinateurDetails.getStatut());
        ordinateur.setMarque(ordinateurDetails.getMarque());
        ordinateur.setModele(ordinateurDetails.getModele());
        ordinateur.setNumeroSerie(ordinateurDetails.getNumeroSerie());
        ordinateur.setProcesseur(ordinateurDetails.getProcesseur());
        ordinateur.setRam(ordinateurDetails.getRam());
        ordinateur.setStockage(ordinateurDetails.getStockage());
        ordinateur.setSystemeExploitation(ordinateurDetails.getSystemeExploitation());
        ordinateur.setCommentaire(ordinateurDetails.getCommentaire());
        ordinateur.setUtilisateur(ordinateurDetails.getUtilisateur());
        ordinateur.setDateModification(LocalDateTime.now());
        
        return ordinateurRepository.save(ordinateur);
    }

    public void deleteOrdinateur(Long id) {
        if (!ordinateurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ordinateur", "id", id);
        }
        ordinateurRepository.deleteById(id);
    }

    public List<String> getAllTypes() {
        return ordinateurRepository.findAllTypes();
    }

    public List<String> getAllStatuts() {
        return ordinateurRepository.findAllStatuts();
    }

    public Page<Ordinateur> searchOrdinateurs(String type, String statut, String marque, String modele, Pageable pageable) {
        return ordinateurRepository.searchOrdinateurs(type, statut, marque, modele, pageable);
    }
} 