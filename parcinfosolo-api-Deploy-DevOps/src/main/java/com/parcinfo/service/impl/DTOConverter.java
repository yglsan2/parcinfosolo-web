package com.parcinfo.service.impl;

import com.parcinfo.dto.AffectationDTO;
import com.parcinfo.model.Affectation;
import com.parcinfo.model.Appareil;
import com.parcinfo.model.Personne;
import org.springframework.stereotype.Service;

@Service
public class DTOConverter {

    public AffectationDTO toAffectationDTO(Affectation affectation) {
        if (affectation == null) {
            return null;
        }

        AffectationDTO dto = new AffectationDTO();
        dto.setId(affectation.getId());
        dto.setDateAffectation(affectation.getDateAffectation());

        Personne personne = affectation.getPersonne();
        if (personne != null) {
            dto.setPersonneId(personne.getId());
            dto.setPersonneNom(personne.getNom());
            dto.setPersonnePrenom(personne.getPrenom());
        }

        Appareil appareil = affectation.getAppareil();
        if (appareil != null) {
            dto.setAppareilId(appareil.getId());
            dto.setAppareilType(appareil.getClass().getSimpleName());
            dto.setAppareilLibelle(appareil.getLibelle());
        }

        return dto;
    }
} 