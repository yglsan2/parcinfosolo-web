package com.parcinfo.service;

import com.parcinfo.model.Emplacement;
import com.parcinfo.repository.EmplacementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmplacementService {
    
    @Autowired
    private EmplacementRepository emplacementRepository;
    
    public List<Emplacement> findAll() {
        return emplacementRepository.findAll();
    }
    
    public Optional<Emplacement> findById(Long id) {
        return emplacementRepository.findById(id);
    }
    
    public List<Emplacement> findByParcId(Long parcId) {
        return emplacementRepository.findByParcIdParc(parcId);
    }
    
    public List<Emplacement> findByTypeEmplacementId(Long typeEmplacementId) {
        return emplacementRepository.findByTypeEmplacementIdTypeEmplacement(typeEmplacementId);
    }
    
    public Emplacement save(Emplacement emplacement) {
        return emplacementRepository.save(emplacement);
    }
    
    public void deleteById(Long id) {
        emplacementRepository.deleteById(id);
    }
} 