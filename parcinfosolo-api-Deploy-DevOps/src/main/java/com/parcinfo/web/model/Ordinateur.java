package com.parcinfo.web.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ordinateurs")
public class Ordinateur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String marque;
    private String modele;
    private String numeroSerie;
    private String processeur;
    private Integer ram;
    private Integer stockage;
    private String systemeExploitation;
    private String etat;
} 