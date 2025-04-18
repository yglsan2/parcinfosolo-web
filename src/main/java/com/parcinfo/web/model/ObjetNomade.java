package com.parcinfo.web.model;

import lombok.Data;

@Data
public class ObjetNomade {
    private Long idObjetNomade;
    private String type;
    private String numeroSerie;
    private String marque;
    private String modele;
    private String etat;
    private String systemeExploitation;
    private String capaciteStockage;
    private Personne personne;
} 