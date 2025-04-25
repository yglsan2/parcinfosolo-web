package com.parcinfo.web.service;

import com.parcinfo.model.Personne;
import com.parcinfo.web.model.PersonneWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonneService {
    private static final Logger logger = LoggerFactory.getLogger(PersonneService.class);
    private final com.parcinfo.service.PersonneService apiPersonneService;

    public PersonneService(com.parcinfo.service.PersonneService apiPersonneService) {
        this.apiPersonneService = apiPersonneService;
    }

    public List<PersonneWeb> findAll() {
        logger.debug("Récupération de toutes les personnes");
        return apiPersonneService.findAll().stream()
                .map(PersonneWeb::new)
                .collect(Collectors.toList());
    }

    public PersonneWeb findById(Long id) {
        logger.debug("Recherche de la personne avec l'id: {}", id);
        return apiPersonneService.findById(id)
                .map(PersonneWeb::new)
                .orElseThrow(() -> new RuntimeException("Personne non trouvée avec l'id : " + id));
    }

    public PersonneWeb save(PersonneWeb personneWeb) {
        logger.debug("Sauvegarde de la personne: {}", personneWeb);
        Personne personne = apiPersonneService.save(personneWeb.toPersonne());
        return new PersonneWeb(personne);
    }

    public void deleteById(Long id) {
        logger.debug("Suppression de la personne avec l'id: {}", id);
        apiPersonneService.deleteById(id);
    }
} 