package com.parcinfo.model;

public enum TypeObjetNomade {
    SMARTPHONE("Smartphone"),
    TABLETTE("Tablette"),
    LAPTOP("Laptop"),
    MONTRE_CONNECTEE("Montre connectée"),
    LUNETTES_CONNECTEES("Lunettes connectées"),
    LECTEUR_MP3("Lecteur MP3"),
    LECTEUR_MP4("Lecteur MP4"),
    CONSOLE_PORTABLE("Console portable"),
    CAMERA_ACTION("Caméra d'action"),
    DRONE("Drone"),
    ENCEINTE_NOMADE("Enceinte nomade"),
    CAISSE_ENREGISTREUSE("Caisse enregistreuse"),
    TERMINAL_PAIEMENT("Terminal de paiement"),
    SCANNER_CODE_BARRE("Scanner de code-barres"),
    IMPRIMANTE_THERMIQUE("Imprimante thermique");

    private final String libelle;

    TypeObjetNomade(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
} 