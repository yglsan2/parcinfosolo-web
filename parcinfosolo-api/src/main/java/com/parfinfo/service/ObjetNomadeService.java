package com.parfinfo.service;

import com.parfinfo.dto.objetnomade.CreateObjetNomadeRequest;
import com.parfinfo.dto.objetnomade.ObjetNomadeResponse;
import com.parfinfo.dto.objetnomade.UpdateObjetNomadeRequest;
import com.parfinfo.entity.EtatEquipement;
import com.parfinfo.entity.ObjetNomade;
import com.parfinfo.entity.TypeObjetNomade;
import com.parfinfo.repository.ObjetNomadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ObjetNomadeService {

    private final ObjetNomadeRepository objetNomadeRepository;

    @Transactional(readOnly = true)
    public Page<ObjetNomadeResponse> getAllObjetsNomades(Pageable pageable) {
        return objetNomadeRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public ObjetNomadeResponse getObjetNomadeById(Long id) {
        return objetNomadeRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Objet nomade non trouvé"));
    }

    @Transactional
    public ObjetNomadeResponse createObjetNomade(CreateObjetNomadeRequest request) {
        ObjetNomade objetNomade = new ObjetNomade();
        updateObjetNomadeFromRequest(objetNomade, request);
        return mapToResponse(objetNomadeRepository.save(objetNomade));
    }

    @Transactional
    public ObjetNomadeResponse updateObjetNomade(Long id, UpdateObjetNomadeRequest request) {
        ObjetNomade objetNomade = objetNomadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Objet nomade non trouvé"));
        updateObjetNomadeFromRequest(objetNomade, request);
        return mapToResponse(objetNomadeRepository.save(objetNomade));
    }

    @Transactional
    public void deleteObjetNomade(Long id) {
        objetNomadeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<ObjetNomadeResponse> searchObjetsNomades(
            TypeObjetNomade type,
            EtatEquipement statut,
            String marque,
            String modele,
            Pageable pageable) {
        return objetNomadeRepository.searchObjetsNomades(type, statut, marque, modele, pageable)
                .map(this::mapToResponse);
    }

    private void updateObjetNomadeFromRequest(ObjetNomade objetNomade, CreateObjetNomadeRequest request) {
        objetNomade.setType(request.getType());
        objetNomade.setStatut(request.getStatut());
        objetNomade.setMarque(request.getMarque());
        objetNomade.setModele(request.getModele());
        objetNomade.setNumeroSerie(request.getNumeroSerie());
        objetNomade.setCommentaire(request.getCommentaire());
    }

    private void updateObjetNomadeFromRequest(ObjetNomade objetNomade, UpdateObjetNomadeRequest request) {
        if (request.getType() != null) objetNomade.setType(request.getType());
        if (request.getStatut() != null) objetNomade.setStatut(request.getStatut());
        if (request.getMarque() != null) objetNomade.setMarque(request.getMarque());
        if (request.getModele() != null) objetNomade.setModele(request.getModele());
        if (request.getNumeroSerie() != null) objetNomade.setNumeroSerie(request.getNumeroSerie());
        if (request.getCommentaire() != null) objetNomade.setCommentaire(request.getCommentaire());
    }

    private ObjetNomadeResponse mapToResponse(ObjetNomade objetNomade) {
        ObjetNomadeResponse response = new ObjetNomadeResponse();
        response.setId(objetNomade.getId());
        response.setType(objetNomade.getType());
        response.setStatut(objetNomade.getStatut());
        response.setMarque(objetNomade.getMarque());
        response.setModele(objetNomade.getModele());
        response.setNumeroSerie(objetNomade.getNumeroSerie());
        response.setCommentaire(objetNomade.getCommentaire());
        response.setDateCreation(objetNomade.getDateCreation());
        response.setDateModification(objetNomade.getDateModification());
        return response;
    }
} 