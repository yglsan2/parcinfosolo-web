package com.parcinfo.model;

public enum TypePeripherique {
    // Périphériques pour ordinateurs
    CLAVIER("Clavier"),
    SOURIS("Souris"),
    ECRAN("Écran"),
    IMPRIMANTE("Imprimante"),
    SCANNEUR("Scanner"),
    HAUT_PARLEUR("Haut-parleur"),
    MICROPHONE("Microphone"),
    WEBCAM("Webcam"),
    CASQUE("Casque"),
    TOUCHPAD("Touchpad"),
    STOCKAGE_EXTERNE("Stockage externe"),
    CARTE_GRAPHIQUE("Carte graphique"),
    CARTE_RESEAU("Carte réseau"),
    CARTE_SON("Carte son"),
    
    // Périphériques pour objets nomades
    COQUE("Coque"),
    PROTECTION_ECRAN("Protection d'écran"),
    CHARGEUR("Chargeur"),
    BATTERIE_EXTERNE("Batterie externe"),
    SUPPORT("Support"),
    ETUI("Étui"),
    STYLET("Stylet"),
    ADAPTATEUR("Adaptateur"),
    HUB_USB("Hub USB"),
    ENCEINTE_BLUETOOTH("Enceinte Bluetooth"),
    ECOUTEURS("Écouteurs"),
    STATION_CHARGEMENT("Station de chargement");

    private final String libelle;

    TypePeripherique(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
} 