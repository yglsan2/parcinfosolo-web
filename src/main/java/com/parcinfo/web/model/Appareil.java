package com.parcinfo.web.model;

public class Appareil {
    private Long idAppareil;
    private String type;
    private String numeroSerie;
    private String marque;
    private String modele;
    private String etat;
    private Personne personne;

    // Constructeurs
    public Appareil() {
    }

    public Appareil(Long idAppareil, String type, String numeroSerie, String marque, String modele, String etat) {
        this.idAppareil = idAppareil;
        this.type = type;
        this.numeroSerie = numeroSerie;
        this.marque = marque;
        this.modele = modele;
        this.etat = etat;
    }

    // Getters et Setters
    public Long getIdAppareil() {
        return idAppareil;
    }

    public void setIdAppareil(Long idAppareil) {
        this.idAppareil = idAppareil;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    // Méthodes utilitaires
    @Override
    public String toString() {
        return "Appareil{" +
                "idAppareil=" + idAppareil +
                ", type='" + type + '\'' +
                ", numeroSerie='" + numeroSerie + '\'' +
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", etat='" + etat + '\'' +
                '}';
    }
} 