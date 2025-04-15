package com.parfinfo.service;

import com.parfinfo.dto.appareil.AppareilResponse;
import com.parfinfo.dto.appareil.CreateAppareilRequest;
import com.parfinfo.dto.appareil.UpdateAppareilRequest;
import com.parfinfo.entity.Appareil;
import com.parfinfo.entity.Ordinateur;
import com.parfinfo.entity.ObjetNomade;
import com.parfinfo.model.AppareilStandard;
import com.parfinfo.model.TypeAppareil;
import com.parfinfo.model.EtatAppareil;
import com.parfinfo.repository.AppareilRepository;
import com.parfinfo.repository.OrdinateurRepository;
import com.parfinfo.repository.ObjetNomadeRepository;
import com.parfinfo.repository.AppareilStandardRepository;
import com.parfinfo.exception.ApiException;
import com.parfinfo.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        try {
            if (!appareilRepository.existsById(id)) {
                throw new ResourceNotFoundException("Appareil", "id", id);
            }
            appareilRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la suppression de l'appareil")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("APPAREIL_DELETE_ERROR")
                .details(e.getMessage())
                .build();
        }
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

    public Page<Appareil> findAll(Pageable pageable) {
        try {
            return appareilRepository.findAll(pageable);
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

    public List<Appareil> findByType(TypeAppareil type) {
        try {
            return appareilRepository.findByType(type);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération des appareils par type")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("APPAREIL_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public List<Appareil> findByEtat(EtatAppareil etat) {
        try {
            return appareilRepository.findByEtat(etat);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération des appareils par état")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("APPAREIL_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public List<TypeAppareil> getAllTypes() {
        return List.of(TypeAppareil.values());
    }

    public List<EtatAppareil> getAllEtats() {
        return List.of(EtatAppareil.values());
    }

    @Transactional(readOnly = true)
    public Page<AppareilResponse> getAllAppareils(Pageable pageable) {
        return appareilRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public AppareilResponse getAppareilById(Long id) {
        return appareilRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Appareil non trouvé"));
    }

    @Transactional
    public AppareilResponse createAppareil(CreateAppareilRequest request) {
        Appareil appareil = new Appareil();
        updateAppareilFromRequest(appareil, request);
        appareil.setDateCreation(LocalDateTime.now());
        return mapToResponse(appareilRepository.save(appareil));
    }

    @Transactional
    public AppareilResponse updateAppareil(Long id, UpdateAppareilRequest request) {
        Appareil appareil = appareilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appareil non trouvé"));
        updateAppareilFromRequest(appareil, request);
        appareil.setDateModification(LocalDateTime.now());
        return mapToResponse(appareilRepository.save(appareil));
    }

    @Transactional(readOnly = true)
    public List<AppareilResponse> searchAppareils(String type, String status) {
        return appareilRepository.findByTypeAndStatus(type, status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> getAllTypes() {
        return appareilRepository.findAllTypes();
    }

    @Transactional(readOnly = true)
    public List<String> getAllStatus() {
        return appareilRepository.findAllStatus();
    }

    private void updateAppareilFromRequest(Appareil appareil, CreateAppareilRequest request) {
        appareil.setNom(request.getNom());
        appareil.setType(request.getType());
        appareil.setMarque(request.getMarque());
        appareil.setModele(request.getModele());
        appareil.setNumeroSerie(request.getNumeroSerie());
        appareil.setStatus(request.getStatus());
        appareil.setLocalisation(request.getLocalisation());
        appareil.setDescription(request.getDescription());
        appareil.setDateAcquisition(request.getDateAcquisition());
        appareil.setDateDerniereMaintenance(request.getDateDerniereMaintenance());
        appareil.setDateProchaineMaintenance(request.getDateProchaineMaintenance());
        appareil.setFournisseur(request.getFournisseur());
        appareil.setCoutAcquisition(request.getCoutAcquisition());
        appareil.setGarantie(request.getGarantie());
        appareil.setNotes(request.getNotes());
    }

    private void updateAppareilFromRequest(Appareil appareil, UpdateAppareilRequest request) {
        if (request.getNom() != null) appareil.setNom(request.getNom());
        if (request.getType() != null) appareil.setType(request.getType());
        if (request.getMarque() != null) appareil.setMarque(request.getMarque());
        if (request.getModele() != null) appareil.setModele(request.getModele());
        if (request.getNumeroSerie() != null) appareil.setNumeroSerie(request.getNumeroSerie());
        if (request.getStatus() != null) appareil.setStatus(request.getStatus());
        if (request.getLocalisation() != null) appareil.setLocalisation(request.getLocalisation());
        if (request.getDescription() != null) appareil.setDescription(request.getDescription());
        if (request.getDateAcquisition() != null) appareil.setDateAcquisition(request.getDateAcquisition());
        if (request.getDateDerniereMaintenance() != null) appareil.setDateDerniereMaintenance(request.getDateDerniereMaintenance());
        if (request.getDateProchaineMaintenance() != null) appareil.setDateProchaineMaintenance(request.getDateProchaineMaintenance());
        if (request.getFournisseur() != null) appareil.setFournisseur(request.getFournisseur());
        if (request.getCoutAcquisition() != null) appareil.setCoutAcquisition(request.getCoutAcquisition());
        if (request.getGarantie() != null) appareil.setGarantie(request.getGarantie());
        if (request.getNotes() != null) appareil.setNotes(request.getNotes());
    }

    private AppareilResponse mapToResponse(Appareil appareil) {
        AppareilResponse response = new AppareilResponse();
        response.setId(appareil.getId());
        response.setNom(appareil.getNom());
        response.setType(appareil.getType());
        response.setMarque(appareil.getMarque());
        response.setModele(appareil.getModele());
        response.setNumeroSerie(appareil.getNumeroSerie());
        response.setStatus(appareil.getStatus());
        response.setLocalisation(appareil.getLocalisation());
        response.setDescription(appareil.getDescription());
        response.setDateAcquisition(appareil.getDateAcquisition());
        response.setDateDerniereMaintenance(appareil.getDateDerniereMaintenance());
        response.setDateProchaineMaintenance(appareil.getDateProchaineMaintenance());
        response.setFournisseur(appareil.getFournisseur());
        response.setCoutAcquisition(appareil.getCoutAcquisition());
        response.setGarantie(appareil.getGarantie());
        response.setNotes(appareil.getNotes());
        response.setDateCreation(appareil.getDateCreation());
        response.setDateModification(appareil.getDateModification());
        return response;
    }
} 