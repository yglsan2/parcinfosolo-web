package com.parcinfo.service;

import com.parcinfo.model.InterventionObjetNomade;
import com.parcinfo.repository.InterventionObjetNomadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterventionObjetNomadeService {

    @Autowired
    private InterventionObjetNomadeRepository interventionObjetNomadeRepository;

    public List<InterventionObjetNomade> findAll() {
        return interventionObjetNomadeRepository.findAll();
    }

    public Optional<InterventionObjetNomade> findById(Long id) {
        return interventionObjetNomadeRepository.findById(id);
    }

    public InterventionObjetNomade save(InterventionObjetNomade intervention) {
        return interventionObjetNomadeRepository.save(intervention);
    }

    public void deleteById(Long id) {
        interventionObjetNomadeRepository.deleteById(id);
    }
} 