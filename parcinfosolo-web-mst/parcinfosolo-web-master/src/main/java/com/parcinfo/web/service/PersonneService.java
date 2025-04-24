package com.parcinfo.web.service;

import com.parcinfo.web.model.Personne;
import com.parcinfo.web.repository.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonneService {

    @Autowired
    private PersonneRepository personneRepository;

    public List<Personne> findAll() {
        return personneRepository.findAll();
    }

    public Personne findById(Long id) {
        return personneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée avec l'id : " + id));
    }

    public Personne save(Personne personne) {
        return personneRepository.save(personne);
    }

    public void deleteById(Long id) {
        personneRepository.deleteById(id);
    }
} 