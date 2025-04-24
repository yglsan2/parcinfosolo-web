package com.parcinfo.service;

import com.parcinfo.model.Appareil;
import com.parcinfo.model.Ordinateur;
import com.parcinfo.model.ObjetNomade;
import com.parcinfo.repository.AppareilRepository;
import com.parcinfo.repository.OrdinateurRepository;
import com.parcinfo.repository.ObjetNomadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppareilService {
    
    @Autowired
    private AppareilRepository appareilRepository;
    
    @Autowired
    private OrdinateurRepository ordinateurRepository;
    
    @Autowired
    private ObjetNomadeRepository objetNomadeRepository;
    
    public List<Appareil> findAll() {
        return appareilRepository.findAll();
    }
    
    public Optional<Appareil> findById(Long id) {
        return appareilRepository.findById(id);
    }
    
    public Appareil save(Appareil appareil) {
        return appareilRepository.save(appareil);
    }
    
    public void deleteById(Long id) {
        appareilRepository.deleteById(id);
    }
    
    public List<Ordinateur> findAllOrdinateurs() {
        return ordinateurRepository.findAll();
    }
    
    public Optional<Ordinateur> findOrdinateurById(Long id) {
        return ordinateurRepository.findById(id);
    }
    
    public Ordinateur saveOrdinateur(Ordinateur ordinateur) {
        return ordinateurRepository.save(ordinateur);
    }
    
    public List<ObjetNomade> findAllObjetsNomades() {
        return objetNomadeRepository.findAll();
    }
    
    public Optional<ObjetNomade> findObjetNomadeById(Long id) {
        return objetNomadeRepository.findById(id);
    }
    
    public ObjetNomade saveObjetNomade(ObjetNomade objetNomade) {
        return objetNomadeRepository.save(objetNomade);
    }
} 