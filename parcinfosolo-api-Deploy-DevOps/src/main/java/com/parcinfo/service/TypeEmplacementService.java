package com.parcinfo.service;

import com.parcinfo.model.TypeEmplacement;
import com.parcinfo.repository.TypeEmplacementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeEmplacementService {
    
    @Autowired
    private TypeEmplacementRepository typeEmplacementRepository;
    
    public List<TypeEmplacement> findAll() {
        return typeEmplacementRepository.findAll();
    }
    
    public Optional<TypeEmplacement> findById(Long id) {
        return typeEmplacementRepository.findById(id);
    }
    
    public TypeEmplacement save(TypeEmplacement typeEmplacement) {
        return typeEmplacementRepository.save(typeEmplacement);
    }
    
    public void deleteById(Long id) {
        typeEmplacementRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return typeEmplacementRepository.existsById(id);
    }
} 