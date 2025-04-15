package com.parfinfo.service;

import com.parfinfo.dto.ObjetNomadeRequest;
import com.parfinfo.dto.ObjetNomadeResponse;
import com.parfinfo.entity.ObjetNomade;
import com.parfinfo.entity.Utilisateur;
import com.parfinfo.repository.ObjetNomadeRepository;
import com.parfinfo.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObjetNomadeService {
    private final ObjetNomadeRepository objetNomadeRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Transactional(readOnly = true)
    public List<ObjetNomadeResponse> getAllObjetsNomades() {
        return objetNomadeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ObjetNomadeResponse getObjetNomadeById(Long id) {
        return objetNomadeRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Objet nomade non trouvé"));
    }

    @Transactional
    public ObjetNomadeResponse createObjetNomade(ObjetNomadeRequest request) {
        ObjetNomade objetNomade = new ObjetNomade();
        updateObjetNomadeFromRequest(objetNomade, request);
        objetNomade.setDateCreation(LocalDateTime.now());
        return mapToResponse(objetNomadeRepository.save(objetNomade));
    }

    @Transactional
    public ObjetNomadeResponse updateObjetNomade(Long id, ObjetNomadeRequest request) {
        ObjetNomade objetNomade = objetNomadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Objet nomade non trouvé"));
        updateObjetNomadeFromRequest(objetNomade, request);
        objetNomade.setDateModification(LocalDateTime.now());
        return mapToResponse(objetNomadeRepository.save(objetNomade));
    }

    @Transactional
    public void deleteObjetNomade(Long id) {
        objetNomadeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<String> getAllTypes() {
        return objetNomadeRepository.findAllTypes();
    }

    @Transactional(readOnly = true)
    public List<String> getAllStatuts() {
        return objetNomadeRepository.findAllStatuts();
    }

    private void updateObjetNomadeFromRequest(ObjetNomade objetNomade, ObjetNomadeRequest request) {
        objetNomade.setType(request.getType());
        objetNomade.setStatut(request.getStatut());
        objetNomade.setMarque(request.getMarque());
        objetNomade.setModele(request.getModele());
        objetNomade.setNumeroSerie(request.getNumeroSerie());
        objetNomade.setCommentaire(request.getCommentaire());
        
        if (request.getUtilisateurId() != null) {
            Utilisateur utilisateur = utilisateurRepository.findById(request.getUtilisateurId())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            objetNomade.setUtilisateur(utilisateur);
        }
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
        
        if (objetNomade.getUtilisateur() != null) {
            response.setUtilisateurId(objetNomade.getUtilisateur().getId());
            response.setUtilisateurNom(objetNomade.getUtilisateur().getNom());
        }
        
        return response;
    }
} 