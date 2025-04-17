package com.parcinfo.service;

import com.parcinfo.model.Peripherique;
import com.parcinfo.model.TypePeripherique;
import com.parcinfo.repository.PeripheriqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PeripheriqueService {
    private final PeripheriqueRepository peripheriqueRepository;

    public List<Peripherique> getAllPeripheriques() {
        return peripheriqueRepository.findAll();
    }

    public Optional<Peripherique> getPeripheriqueById(Long id) {
        return peripheriqueRepository.findById(id);
    }

    public Optional<Peripherique> getPeripheriqueByNumeroSerie(String numeroSerie) {
        return peripheriqueRepository.findByNumeroSerie(numeroSerie);
    }

    public List<Peripherique> getPeripheriquesByType(TypePeripherique type) {
        return peripheriqueRepository.findByType(type);
    }

    public List<Peripherique> getPeripheriquesByMarque(String marque) {
        return peripheriqueRepository.findByMarque(marque);
    }

    public List<Peripherique> getPeripheriquesActifs() {
        return peripheriqueRepository.findByEstActif(true);
    }

    public List<Peripherique> getPeripheriquesByObjetNomade(Long idObjetNomade) {
        return peripheriqueRepository.findByObjetNomadeIdObjetNomade(idObjetNomade);
    }

    public List<Peripherique> getPeripheriquesByOrdinateur(Long idOrdinateur) {
        return peripheriqueRepository.findByOrdinateurIdOrdinateur(idOrdinateur);
    }

    @Transactional
    public Peripherique createPeripherique(Peripherique peripherique) {
        return peripheriqueRepository.save(peripherique);
    }

    @Transactional
    public Peripherique updatePeripherique(Long id, Peripherique peripheriqueDetails) {
        return peripheriqueRepository.findById(id)
                .map(peripherique -> {
                    peripherique.setType(peripheriqueDetails.getType());
                    peripherique.setMarque(peripheriqueDetails.getMarque());
                    peripherique.setModele(peripheriqueDetails.getModele());
                    peripherique.setNumeroSerie(peripheriqueDetails.getNumeroSerie());
                    peripherique.setDateAcquisition(peripheriqueDetails.getDateAcquisition());
                    peripherique.setDateMiseEnService(peripheriqueDetails.getDateMiseEnService());
                    peripherique.setEtat(peripheriqueDetails.getEtat());
                    peripherique.setCommentaire(peripheriqueDetails.getCommentaire());
                    peripherique.setEstActif(peripheriqueDetails.isEstActif());
                    return peripheriqueRepository.save(peripherique);
                })
                .orElseThrow(() -> new RuntimeException("Périphérique non trouvé avec l'id : " + id));
    }

    @Transactional
    public void deletePeripherique(Long id) {
        Peripherique peripherique = peripheriqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Périphérique non trouvé avec l'id : " + id));
        peripherique.setEstActif(false);
        peripheriqueRepository.save(peripherique);
    }
} 