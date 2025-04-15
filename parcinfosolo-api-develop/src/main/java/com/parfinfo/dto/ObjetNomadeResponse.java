package com.parfinfo.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ObjetNomadeResponse {
    private Long id;
    private String type;
    private String statut;
    private String marque;
    private String modele;
    private String numeroSerie;
    private String commentaire;
    private Long utilisateurId;
    private String utilisateurNom;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
} 