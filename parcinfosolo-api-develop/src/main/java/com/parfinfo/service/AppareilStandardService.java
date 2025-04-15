package com.parfinfo.service;

import com.parfinfo.model.AppareilStandard;
import com.parfinfo.model.TypeAppareil;
import com.parfinfo.repository.AppareilStandardRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppareilStandardService {

    private final AppareilStandardRepository appareilStandardRepository;

    public List<AppareilStandard> getAllAppareilsStandard() {
        return appareilStandardRepository.findAll();
    }

    public Optional<AppareilStandard> getAppareilStandardById(Long id) {
        return appareilStandardRepository.findById(id);
    }

    public List<AppareilStandard> getAppareilsStandardByType(TypeAppareil type) {
        return appareilStandardRepository.findByType(type);
    }

    @Transactional
    public AppareilStandard saveAppareilStandard(AppareilStandard appareilStandard) {
        if (appareilStandard.getId() == null) {
            // Nouvel appareil
            if (appareilStandardRepository.existsByNumeroSerie(appareilStandard.getNumeroSerie())) {
                throw new EntityExistsException("Un appareil avec ce numéro de série existe déjà");
            }
        } else {
            // Mise à jour
            Optional<AppareilStandard> existingAppareil = appareilStandardRepository.findById(appareilStandard.getId());
            if (existingAppareil.isEmpty()) {
                throw new EntityNotFoundException("Appareil non trouvé");
            }
            
            // Vérifier si le numéro de série a changé et s'il existe déjà
            if (!existingAppareil.get().getNumeroSerie().equals(appareilStandard.getNumeroSerie()) &&
                appareilStandardRepository.existsByNumeroSerie(appareilStandard.getNumeroSerie())) {
                throw new EntityExistsException("Un appareil avec ce numéro de série existe déjà");
            }
        }
        
        return appareilStandardRepository.save(appareilStandard);
    }

    @Transactional
    public void deleteAppareilStandard(Long id) {
        if (!appareilStandardRepository.existsById(id)) {
            throw new EntityNotFoundException("Appareil non trouvé");
        }
        appareilStandardRepository.deleteById(id);
    }
} 