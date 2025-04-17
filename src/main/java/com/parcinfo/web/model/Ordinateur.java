package com.parcinfo.web.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Ordinateur extends Appareil {
    private String processeur;
    private String ram;
    private String disqueDur;
    private String carteGraphique;
    private String systemeExploitation;
} 