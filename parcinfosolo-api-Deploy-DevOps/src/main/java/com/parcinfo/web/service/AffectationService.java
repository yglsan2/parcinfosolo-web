package com.parcinfo.web.service;

import com.parcinfo.model.Affectation;
import com.parcinfo.repository.AffectationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AffectationService {

    @Autowired
    private AffectationRepository affectationRepository;

    public List<Affectation> findAll() {
        return affectationRepository.findAll();
    }

    public Affectation findById(Long id) {
        return affectationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Affectation non trouvée avec l'id: " + id));
    }

    public List<Affectation> findByPersonneId(Long personneId) {
        return affectationRepository.findByPersonneIdPersonne(personneId);
    }

    public List<Affectation> findByAppareilId(Long appareilId) {
        return affectationRepository.findByAppareilIdAppareil(appareilId);
    }

    public Affectation save(Affectation affectation) {
        return affectationRepository.save(affectation);
    }

    public void deleteById(Long id) {
        affectationRepository.deleteById(id);
    }
} 