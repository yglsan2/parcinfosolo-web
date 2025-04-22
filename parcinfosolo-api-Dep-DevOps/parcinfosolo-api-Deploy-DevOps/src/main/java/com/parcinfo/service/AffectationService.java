package com.parcinfo.service;

import com.parcinfo.model.Affectation;
import com.parcinfo.model.Appareil;
import com.parcinfo.model.Personne;
import com.parcinfo.repository.AffectationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AffectationService {
    
    @Autowired
    private AffectationRepository affectationRepository;
    
    @Autowired
    private PersonneService personneService;
    
    @Autowired
    private AppareilService appareilService;
    
    public List<Affectation> findAll() {
        return affectationRepository.findAll();
    }
    
    public Optional<Affectation> findById(Long id) {
        return affectationRepository.findById(id);
    }
    
    public List<Affectation> findByPersonneId(Long personneId) {
        return affectationRepository.findByPersonneIdPersonne(personneId);
    }
    
    public List<Affectation> findByAppareilId(Long appareilId) {
        return affectationRepository.findByAppareilIdAppareil(appareilId);
    }
    
    public Affectation createAffectation(Long personneId, Long appareilId) {
        Personne personne = personneService.findById(personneId)
            .orElseThrow(() -> new RuntimeException("Personne non trouvée"));
            
        Appareil appareil = appareilService.findById(appareilId)
            .orElseThrow(() -> new RuntimeException("Appareil non trouvé"));
            
        Affectation affectation = new Affectation();
        affectation.setPersonne(personne);
        affectation.setAppareil(appareil);
        affectation.setDateAffectation(LocalDate.now());
        
        return affectationRepository.save(affectation);
    }
    
    public void deleteById(Long id) {
        affectationRepository.deleteById(id);
    }
} 