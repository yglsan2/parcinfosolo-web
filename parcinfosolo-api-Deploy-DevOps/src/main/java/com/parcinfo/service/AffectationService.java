package com.parcinfo.service;

import com.parcinfo.model.Affectation;
import com.parcinfo.repository.AffectationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AffectationService {
    
    private final AffectationRepository affectationRepository;

    public AffectationService(AffectationRepository affectationRepository) {
        this.affectationRepository = affectationRepository;
    }

    public List<Affectation> findAll() {
        return affectationRepository.findAll();
    }

    public Optional<Affectation> findById(Long id) {
        return affectationRepository.findById(id);
    }

    public Affectation save(Affectation affectation) {
        return affectationRepository.save(affectation);
    }

    public void deleteById(Long id) {
        affectationRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return affectationRepository.existsById(id);
    }
} 