package com.parcinfo.service;

import com.parcinfo.model.Personne;
import com.parcinfo.repository.PersonneRepository;
import com.parcinfo.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonneService {
    private static final Logger logger = LoggerFactory.getLogger(PersonneService.class);
    private final PersonneRepository personneRepository;

    public PersonneService(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    public List<Personne> findAll() {
        logger.debug("Récupération de toutes les personnes");
        return personneRepository.findAll();
    }

    public Optional<Personne> findById(Long id) {
        logger.debug("Recherche de la personne avec l'id: {}", id);
        if (id == null) {
            throw new BusinessException("L'identifiant de la personne ne peut pas être null");
        }
        return personneRepository.findById(id);
    }

    public Personne save(Personne personne) {
        logger.debug("Sauvegarde de la personne: {}", personne);
        validatePersonne(personne);
        return personneRepository.save(personne);
    }

    public void deleteById(Long id) {
        logger.debug("Suppression de la personne avec l'id: {}", id);
        if (id == null) {
            throw new BusinessException("L'identifiant de la personne ne peut pas être null");
        }
        if (!personneRepository.existsById(id)) {
            throw new BusinessException("La personne avec l'id " + id + " n'existe pas");
        }
        personneRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return personneRepository.existsById(id);
    }

    private void validatePersonne(Personne personne) {
        if (personne == null) {
            throw new BusinessException("La personne ne peut pas être null");
        }
        if (!StringUtils.hasText(personne.getNom())) {
            throw new BusinessException("Le nom de la personne est obligatoire");
        }
        if (!StringUtils.hasText(personne.getPrenom())) {
            throw new BusinessException("Le prénom de la personne est obligatoire");
        }
        if (!StringUtils.hasText(personne.getEmail())) {
            throw new BusinessException("L'email de la personne est obligatoire");
        }
        // Vérification de l'unicité de l'email
        personneRepository.findByEmail(personne.getEmail())
            .ifPresent(existingPersonne -> {
                if (!existingPersonne.getId().equals(personne.getId())) {
                    throw new BusinessException("Une personne avec cet email existe déjà");
                }
            });
    }
} 