package com.parcinfo.service;

import com.parcinfo.model.Parc;
import com.parcinfo.repository.ParcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParcService {
    
    @Autowired
    private ParcRepository parcRepository;
    
    public List<Parc> findAll() {
        return parcRepository.findAll();
    }
    
    public Optional<Parc> findById(Long id) {
        return parcRepository.findById(id);
    }
    
    public Parc save(Parc parc) {
        return parcRepository.save(parc);
    }
    
    public void deleteById(Long id) {
        parcRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return parcRepository.existsById(id);
    }
} 