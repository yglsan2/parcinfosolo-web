package com.parcinfo.web.service;

import com.parcinfo.web.model.Ordinateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdinateurService {

    @Autowired
    private ApiService apiService;

    public List<Ordinateur> findAll() {
        return apiService.getAllAppareils().stream()
                .filter(appareil -> appareil instanceof Ordinateur)
                .map(appareil -> (Ordinateur) appareil)
                .collect(Collectors.toList());
    }

    public Ordinateur findById(Long id) {
        return (Ordinateur) apiService.getAppareilById(id);
    }

    public Ordinateur save(Ordinateur ordinateur) {
        if (ordinateur.getIdAppareil() == null) {
            return (Ordinateur) apiService.createAppareil(ordinateur);
        } else {
            return (Ordinateur) apiService.updateAppareil(ordinateur.getIdAppareil(), ordinateur);
        }
    }

    public void deleteById(Long id) {
        apiService.deleteAppareil(id);
    }
} 