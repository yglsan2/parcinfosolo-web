package com.parcinfo.web.model;

import com.parcinfo.model.Personne;

/**
 * Cette classe est un wrapper pour le modèle Personne du module API.
 * Elle permet d'adapter le modèle API aux besoins spécifiques de la couche web.
 */
public class PersonneWeb {
    private Personne personne;

    public PersonneWeb(Personne personne) {
        this.personne = personne != null ? personne : new Personne();
    }

    public Long getId() {
        return personne.getId();
    }

    public void setId(Long id) {
        personne.setId(id);
    }

    public String getNom() {
        return personne.getNom();
    }

    public void setNom(String nom) {
        personne.setNom(nom);
    }

    public String getPrenom() {
        return personne.getPrenom();
    }

    public void setPrenom(String prenom) {
        personne.setPrenom(prenom);
    }

    public String getEmail() {
        return personne.getEmail();
    }

    public void setEmail(String email) {
        personne.setEmail(email);
    }

    public String getTelephone() {
        return personne.getTelephone();
    }

    public void setTelephone(String telephone) {
        personne.setTelephone(telephone);
    }

    public String getAdresse() {
        return personne.getAdresse();
    }

    public void setAdresse(String adresse) {
        personne.setAdresse(adresse);
    }

    public Personne toPersonne() {
        return personne;
    }
} 