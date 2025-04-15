package com.parfinfo.mapper;

import com.parfinfo.dto.appareil.CreateAppareilRequest;
import com.parfinfo.dto.appareil.UpdateAppareilRequest;
import com.parfinfo.dto.appareil.AppareilResponse;
import com.parfinfo.model.Appareil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppareilMapper {
    
    public AppareilResponse toResponse(Appareil appareil) {
        if (appareil == null) {
            return null;
        }
        
        AppareilResponse response = new AppareilResponse();
        response.setId(appareil.getId());
        response.setNom(appareil.getNom());
        response.setType(appareil.getType().toString());
        response.setEtat(appareil.getEtat().toString());
        response.setNumeroSerie(appareil.getNumeroSerie());
        response.setNumeroInventaire(appareil.getNumeroInventaire());
        response.setDateAcquisition(appareil.getDateAcquisition());
        response.setDatePeremption(appareil.getDatePeremption());
        response.setFournisseur(appareil.getFournisseur());
        response.setPrix(appareil.getPrix());
        response.setNotes(appareil.getNotes());
        return response;
    }
    
    public List<AppareilResponse> toResponseList(List<Appareil> appareils) {
        return appareils.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public Page<AppareilResponse> toResponsePage(List<Appareil> appareils, Pageable pageable) {
        List<AppareilResponse> responses = toResponseList(appareils);
        return new PageImpl<>(responses, pageable, responses.size());
    }
    
    public Appareil toEntity(CreateAppareilRequest request) {
        if (request == null) {
            return null;
        }
        
        Appareil appareil = new Appareil();
        appareil.setNom(request.getNom());
        appareil.setType(TypeAppareil.valueOf(request.getType()));
        appareil.setEtat(EtatAppareil.valueOf(request.getEtat()));
        appareil.setNumeroSerie(request.getNumeroSerie());
        appareil.setNumeroInventaire(request.getNumeroInventaire());
        appareil.setDateAcquisition(request.getDateAcquisition());
        appareil.setDatePeremption(request.getDatePeremption());
        appareil.setFournisseur(request.getFournisseur());
        appareil.setPrix(request.getPrix());
        appareil.setNotes(request.getNotes());
        return appareil;
    }
    
    public Appareil toEntity(UpdateAppareilRequest request) {
        if (request == null) {
            return null;
        }
        
        Appareil appareil = new Appareil();
        appareil.setNom(request.getNom());
        appareil.setType(TypeAppareil.valueOf(request.getType()));
        appareil.setEtat(EtatAppareil.valueOf(request.getEtat()));
        appareil.setNumeroSerie(request.getNumeroSerie());
        appareil.setNumeroInventaire(request.getNumeroInventaire());
        appareil.setDateAcquisition(request.getDateAcquisition());
        appareil.setDatePeremption(request.getDatePeremption());
        appareil.setFournisseur(request.getFournisseur());
        appareil.setPrix(request.getPrix());
        appareil.setNotes(request.getNotes());
        return appareil;
    }
} 