package com.parfinfo.service;

import com.parfinfo.model.Appareil;
import com.parfinfo.model.Ordinateur;
import com.parfinfo.model.ObjetNomade;
import com.parfinfo.model.AppareilStandard;
import com.parfinfo.model.TypeAppareil;
import com.parfinfo.repository.AppareilRepository;
import com.parfinfo.repository.OrdinateurRepository;
import com.parfinfo.repository.ObjetNomadeRepository;
import com.parfinfo.repository.AppareilStandardRepository;
import com.parfinfo.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppareilService {
    private final AppareilRepository appareilRepository;
    private final OrdinateurRepository ordinateurRepository;
    private final ObjetNomadeRepository objetNomadeRepository;
    private final AppareilStandardRepository appareilStandardRepository;

    public AppareilService(AppareilRepository appareilRepository,
                          OrdinateurRepository ordinateurRepository,
                          ObjetNomadeRepository objetNomadeRepository,
                          AppareilStandardRepository appareilStandardRepository) {
        this.appareilRepository = appareilRepository;
        this.ordinateurRepository = ordinateurRepository;
        this.objetNomadeRepository = objetNomadeRepository;
        this.appareilStandardRepository = appareilStandardRepository;
    }

    public List<Ordinateur> getAllOrdinateurs() {
        return ordinateurRepository.findAll();
    }

    public List<ObjetNomade> getAllObjetsNomades() {
        return objetNomadeRepository.findAll();
    }

    public Optional<ObjetNomade> getObjetNomadeById(Long id) {
        return objetNomadeRepository.findById(id);
    }

    public ObjetNomade saveObjetNomade(ObjetNomade objetNomade) {
        return objetNomadeRepository.save(objetNomade);
    }

    public void deleteObjetNomade(Long id) {
        objetNomadeRepository.deleteById(id);
    }

    public List<AppareilStandard> getAllAppareilsStandard() {
        return appareilStandardRepository.findAll();
    }

    public Optional<AppareilStandard> getAppareilStandardById(Long id) {
        return appareilStandardRepository.findById(id);
    }

    public AppareilStandard saveAppareilStandard(AppareilStandard appareilStandard) {
        return appareilStandardRepository.save(appareilStandard);
    }

    public void deleteAppareilStandard(Long id) {
        appareilStandardRepository.deleteById(id);
    }

    public List<Appareil> getDerniersAppareils(int limit) {
        return appareilRepository.findTop5ByOrderByIdAppareilDesc();
    }

    public List<Appareil> getOrdinateurs() {
        return appareilRepository.findByType(TypeAppareil.ORDINATEUR);
    }

    public List<Appareil> getSmartphones() {
        return appareilRepository.findByType(TypeAppareil.SMARTPHONE);
    }

    public List<Appareil> getTablettes() {
        return appareilRepository.findByType(TypeAppareil.TABLETTE);
    }

    public List<Appareil> getPeripheriques() {
        return appareilRepository.findByType(TypeAppareil.PERIPHERIQUE);
    }

    public List<Appareil> getEnceintesBluetooth() {
        return appareilRepository.findByType(TypeAppareil.ENCEINTE_BLUETOOTH);
    }

    public List<Appareil> getLecteursMp3() {
        return appareilRepository.findByType(TypeAppareil.LECTEUR_MP3);
    }

    public void deleteAppareil(Long id) {
        appareilRepository.deleteById(id);
    }

    public void deleteById(Long id) {
        deleteAppareil(id);
    }

    public List<Appareil> findAll() {
        try {
            return appareilRepository.findAll();
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération des appareils")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("APPAREIL_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public Optional<Appareil> findById(Long id) {
        try {
            return appareilRepository.findById(id);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération de l'appareil")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("APPAREIL_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public Appareil save(Appareil appareil) {
        try {
            // Validation des champs obligatoires
            if (appareil.getNom() == null || appareil.getNom().trim().isEmpty()) {
                throw new ApiException.Builder()
                    .message("Le nom est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_NOM")
                    .build();
            }
            if (appareil.getType() == null) {
                throw new ApiException.Builder()
                    .message("Le type est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_TYPE")
                    .build();
            }
            if (appareil.getEtat() == null) {
                throw new ApiException.Builder()
                    .message("L'état est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_ETAT")
                    .build();
            }

            // Validation des numéros de série et d'inventaire uniques
            if (appareil.getNumeroSerie() != null && !appareil.getNumeroSerie().trim().isEmpty()) {
                boolean numeroSerieExists = appareilRepository.findAll().stream()
                    .anyMatch(a -> a.getNumeroSerie() != null && 
                                 a.getNumeroSerie().equals(appareil.getNumeroSerie()) &&
                                 !a.getId().equals(appareil.getId()));
                if (numeroSerieExists) {
                    throw new ApiException.Builder()
                        .message("Un appareil avec ce numéro de série existe déjà")
                        .status(HttpStatus.CONFLICT)
                        .code("NUMERO_SERIE_EXISTS")
                        .build();
                }
            }

            if (appareil.getNumeroInventaire() != null && !appareil.getNumeroInventaire().trim().isEmpty()) {
                boolean numeroInventaireExists = appareilRepository.findAll().stream()
                    .anyMatch(a -> a.getNumeroInventaire() != null && 
                                 a.getNumeroInventaire().equals(appareil.getNumeroInventaire()) &&
                                 !a.getId().equals(appareil.getId()));
                if (numeroInventaireExists) {
                    throw new ApiException.Builder()
                        .message("Un appareil avec ce numéro d'inventaire existe déjà")
                        .status(HttpStatus.CONFLICT)
                        .code("NUMERO_INVENTAIRE_EXISTS")
                        .build();
                }
            }

            return appareilRepository.save(appareil);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la sauvegarde de l'appareil")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("APPAREIL_SAVE_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public long count() {
        return appareilRepository.count();
    }

    public long countByType(String type) {
        return appareilRepository.countByType(type);
    }
} 