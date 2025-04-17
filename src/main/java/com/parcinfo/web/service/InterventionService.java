package com.parcinfo.web.service;

import com.parcinfo.web.model.Intervention;
import com.parcinfo.web.repository.InterventionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InterventionService {

    @Autowired
    private InterventionRepository interventionRepository;

    public List<Intervention> findAll() {
        return interventionRepository.findAll();
    }

    public Optional<Intervention> findById(Long id) {
        return interventionRepository.findById(id);
    }

    public List<Intervention> findByPeripheriqueId(Long peripheriqueId) {
        return interventionRepository.findByPeripheriqueIdOrderByDateDesc(peripheriqueId);
    }

    public Intervention save(Intervention intervention) {
        return interventionRepository.save(intervention);
    }

    public void delete(Intervention intervention) {
        interventionRepository.delete(intervention);
    }

    public void deleteById(Long id) {
        interventionRepository.deleteById(id);
    }
} 