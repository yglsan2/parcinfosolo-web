package com.parfinfo.dto.personne;

import lombok.Data;

@Data
public class PersonneSearchRequest {
    private String nom;
    private String prenom;
    private String email;
    private String departement;
    private String role;
    private String statut;
    private String localisation;
} 