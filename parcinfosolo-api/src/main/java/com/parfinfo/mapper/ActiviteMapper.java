package com.parfinfo.mapper;

import com.parfinfo.dto.activite.CreateActiviteRequest;
import com.parfinfo.dto.activite.UpdateActiviteRequest;
import com.parfinfo.dto.activite.ActiviteResponse;
import com.parfinfo.entity.Activite;
import org.springframework.stereotype.Component;

@Component
public class ActiviteMapper {
    
    public ActiviteResponse toResponse(Activite activite) {
        if (activite == null) {
            return null;
        }
        
        ActiviteResponse response = new ActiviteResponse();
        response.setId(activite.getId());
        response.setType(activite.getType());
        response.setDescription(activite.getDescription());
        response.setDateCreation(activite.getDateCreation());
        response.setUtilisateurId(activite.getUtilisateur() != null ? activite.getUtilisateur().getId() : null);
        response.setUtilisateurNom(activite.getUtilisateur() != null ? activite.getUtilisateur().getNom() : null);
        response.setAppareilId(activite.getAppareil() != null ? activite.getAppareil().getId() : null);
        response.setAppareilNom(activite.getAppareil() != null ? activite.getAppareil().getNom() : null);
        return response;
    }
    
    public Activite toEntity(CreateActiviteRequest request) {
        if (request == null) {
            return null;
        }
        
        Activite activite = new Activite();
        activite.setType(request.getType());
        activite.setDescription(request.getDescription());
        return activite;
    }
    
    public Activite toEntity(UpdateActiviteRequest request) {
        if (request == null) {
            return null;
        }
        
        Activite activite = new Activite();
        activite.setType(request.getType());
        activite.setDescription(request.getDescription());
        return activite;
    }
} 