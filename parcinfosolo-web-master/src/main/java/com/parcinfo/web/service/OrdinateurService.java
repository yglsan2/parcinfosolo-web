package com.parcinfo.web.service;

import com.parcinfo.web.model.Ordinateur;
import com.parcinfo.web.repository.OrdinateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdinateurService {

    @Autowired
    private OrdinateurRepository ordinateurRepository;

    public List<Ordinateur> findAll() {
        return ordinateurRepository.findAll();
    }

    public Ordinateur findById(Long id) {
        return ordinateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordinateur non trouvé avec l'id : " + id));
    }

    public Ordinateur save(Ordinateur ordinateur) {
        return ordinateurRepository.save(ordinateur);
    }

    public void deleteById(Long id) {
        ordinateurRepository.deleteById(id);
    }
} 