package com.parcinfo.web.service;

import com.parcinfo.web.model.InterventionObjetNomade;
import com.parcinfo.web.repository.InterventionObjetNomadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InterventionObjetNomadeService {

    @Autowired
    private InterventionObjetNomadeRepository interventionRepository;

    public List<InterventionObjetNomade> findAll() {
        return interventionRepository.findAll();
    }

    public Optional<InterventionObjetNomade> findById(Long id) {
        return interventionRepository.findById(id);
    }

    public List<InterventionObjetNomade> findByObjetNomadeId(Long objetNomadeId) {
        return interventionRepository.findByObjetNomadeId(objetNomadeId);
    }

    @Transactional
    public InterventionObjetNomade save(InterventionObjetNomade intervention) {
        return interventionRepository.save(intervention);
    }

    @Transactional
    public void deleteById(Long id) {
        interventionRepository.deleteById(id);
    }

    @Transactional
    public InterventionObjetNomade update(Long id, InterventionObjetNomade intervention) {
        if (interventionRepository.existsById(id)) {
            intervention.setId(id);
            return interventionRepository.save(intervention);
        }
        return null;
    }
} 