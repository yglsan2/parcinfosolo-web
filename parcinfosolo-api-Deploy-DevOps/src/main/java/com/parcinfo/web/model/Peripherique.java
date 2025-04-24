package com.parcinfo.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Peripherique {
    private Long id;
    
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    
    @NotNull(message = "Le type de périphérique est obligatoire")
    private Long typePeripheriqueId;
    
    private String marque;
    private String modele;
    private String numeroSerie;
    private String description;
    private String etat;
    private Long ordinateurId;
} 