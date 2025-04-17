package com.parcinfo.web.model;

import lombok.Data;

@Data
public class Peripherique {
    private Long idPeripherique;
    private String type;
    private String numeroSerie;
    private String marque;
    private String modele;
    private String etat;
    private String connexion;
    private Ordinateur ordinateur;
} 