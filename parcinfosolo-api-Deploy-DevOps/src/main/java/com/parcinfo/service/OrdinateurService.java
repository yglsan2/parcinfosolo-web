package com.parcinfo.service;

import com.parcinfo.model.Ordinateur;
import com.parcinfo.repository.OrdinateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrdinateurService {

    @Autowired
    private OrdinateurRepository ordinateurRepository;

    public List<Ordinateur> findAll() {
        return ordinateurRepository.findAll();
    }

    public Optional<Ordinateur> findById(Long id) {
        return ordinateurRepository.findById(id);
    }

    public Ordinateur save(Ordinateur ordinateur) {
        return ordinateurRepository.save(ordinateur);
    }

    public void deleteById(Long id) {
        ordinateurRepository.deleteById(id);
    }
} 