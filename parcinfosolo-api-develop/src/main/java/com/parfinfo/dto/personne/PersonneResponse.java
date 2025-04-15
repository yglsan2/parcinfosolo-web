package com.parfinfo.dto.personne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonneResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String service;
    private String poste;
    private String localisation;
    private boolean actif;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 