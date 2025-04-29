package com.parcinfo.web.service;

import com.parcinfo.web.model.ObjetNomade;
import com.parcinfo.web.repository.ObjetNomadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ObjetNomadeService {

    private final ObjetNomadeRepository objetNomadeRepository;

    @Autowired
    public ObjetNomadeService(ObjetNomadeRepository objetNomadeRepository) {
        this.objetNomadeRepository = objetNomadeRepository;
    }

    public List<ObjetNomade> findAll() {
        return objetNomadeRepository.findAll();
    }

    public ObjetNomade findById(Long id) {
        return objetNomadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ObjetNomade non trouvé avec l'ID : " + id));
    }

    public Optional<ObjetNomade> findByNumeroSerie(String numeroSerie) {
        return objetNomadeRepository.findByNumeroSerie(numeroSerie);
    }

    public List<ObjetNomade> findByType(String type) {
        return objetNomadeRepository.findByType(type);
    }

    public List<ObjetNomade> findByMarque(String marque) {
        return objetNomadeRepository.findByMarque(marque);
    }

    public List<ObjetNomade> findByEtat(String etat) {
        return objetNomadeRepository.findByEtat(etat);
    }

    public List<ObjetNomade> findByPersonneId(Long personneId) {
        return objetNomadeRepository.findByPersonneIdPersonne(personneId);
    }

    public ObjetNomade save(ObjetNomade objetNomade) {
        return objetNomadeRepository.save(objetNomade);
    }

    public void deleteById(Long id) {
        objetNomadeRepository.deleteById(id);
    }
} 