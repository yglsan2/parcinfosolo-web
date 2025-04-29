package com.parcinfo.web.service;

import com.parcinfo.web.model.Appareil;
import com.parcinfo.web.repository.AppareilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AppareilService {

    @Autowired
    private AppareilRepository appareilRepository;

    public List<Appareil> findAll() {
        return appareilRepository.findAll();
    }

    public Appareil findById(Long id) {
        return appareilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appareil non trouvé avec l'id: " + id));
    }

    public Appareil save(Appareil appareil) {
        return appareilRepository.save(appareil);
    }

    public void deleteById(Long id) {
        appareilRepository.deleteById(id);
    }
} 