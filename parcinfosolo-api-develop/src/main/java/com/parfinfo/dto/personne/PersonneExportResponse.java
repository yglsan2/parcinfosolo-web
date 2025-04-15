package com.parfinfo.dto.personne;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PersonneExportResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String departement;
    private String role;
    private String statut;
    private String localisation;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private String createur;
    private String modificateur;
    private String historique;
    private String notes;
} 