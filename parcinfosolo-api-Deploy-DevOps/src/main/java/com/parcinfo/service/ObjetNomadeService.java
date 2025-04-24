package com.parcinfo.service;

import com.parcinfo.model.ObjetNomade;
import com.parcinfo.model.Peripherique;
import com.parcinfo.model.TypeObjetNomade;
import com.parcinfo.repository.ObjetNomadeRepository;
import com.parcinfo.repository.PeripheriqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ObjetNomadeService {
    private final ObjetNomadeRepository objetNomadeRepository;
    private final PeripheriqueRepository peripheriqueRepository;

    public List<ObjetNomade> getAllObjetsNomades() {
        return objetNomadeRepository.findAll();
    }

    public Optional<ObjetNomade> getObjetNomadeById(Long id) {
        return objetNomadeRepository.findById(id);
    }

    public Optional<ObjetNomade> getObjetNomadeByNumeroSerie(String numeroSerie) {
        return objetNomadeRepository.findByNumeroSerie(numeroSerie);
    }

    public List<ObjetNomade> getObjetsNomadesByType(TypeObjetNomade type) {
        return objetNomadeRepository.findByType(type);
    }

    public List<ObjetNomade> getObjetsNomadesByMarque(String marque) {
        return objetNomadeRepository.findByMarque(marque);
    }

    public List<ObjetNomade> getObjetsNomadesActifs() {
        return objetNomadeRepository.findByEstActif(true);
    }

    public List<ObjetNomade> getObjetsNomadesByUtilisateur(Long utilisateurId) {
        return objetNomadeRepository.findByUtilisateurIdPersonne(utilisateurId);
    }

    @Transactional
    public ObjetNomade createObjetNomade(ObjetNomade objetNomade) {
        return objetNomadeRepository.save(objetNomade);
    }

    @Transactional
    public ObjetNomade updateObjetNomade(Long id, ObjetNomade objetNomadeDetails) {
        return objetNomadeRepository.findById(id)
                .map(objetNomade -> {
                    objetNomade.setNom(objetNomadeDetails.getNom());
                    objetNomade.setType(objetNomadeDetails.getType());
                    objetNomade.setNumeroSerie(objetNomadeDetails.getNumeroSerie());
                    objetNomade.setMarque(objetNomadeDetails.getMarque());
                    objetNomade.setModele(objetNomadeDetails.getModele());
                    objetNomade.setEtat(objetNomadeDetails.getEtat());
                    objetNomade.setDateAcquisition(objetNomadeDetails.getDateAcquisition());
                    objetNomade.setDateMiseEnService(objetNomadeDetails.getDateMiseEnService());
                    objetNomade.setDateDerniereMaintenance(objetNomadeDetails.getDateDerniereMaintenance());
                    objetNomade.setSystemeExploitation(objetNomadeDetails.getSystemeExploitation());
                    objetNomade.setVersionSysteme(objetNomadeDetails.getVersionSysteme());
                    objetNomade.setEstActif(objetNomadeDetails.isEstActif());
                    objetNomade.setCommentaire(objetNomadeDetails.getCommentaire());
                    return objetNomadeRepository.save(objetNomade);
                })
                .orElseThrow(() -> new RuntimeException("Objet nomade non trouvé avec l'id : " + id));
    }

    @Transactional
    public void deleteObjetNomade(Long id) {
        ObjetNomade objetNomade = objetNomadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Objet nomade non trouvé avec l'id : " + id));
        objetNomade.setEstActif(false);
        objetNomadeRepository.save(objetNomade);
    }

    public List<Peripherique> getPeripheriques(Long idObjetNomade) {
        if (!existsById(idObjetNomade)) {
            throw new RuntimeException("Objet nomade non trouvé");
        }
        return peripheriqueRepository.findByObjetNomadeIdObjetNomade(idObjetNomade);
    }

    public Peripherique ajouterPeripherique(Long idObjetNomade, Peripherique peripherique) {
        ObjetNomade objetNomade = getObjetNomadeById(idObjetNomade)
                .orElseThrow(() -> new RuntimeException("Objet nomade non trouvé"));
        
        peripherique.setObjetNomade(objetNomade);
        return peripheriqueRepository.save(peripherique);
    }

    public boolean existsById(Long id) {
        return objetNomadeRepository.existsById(id);
    }
} 