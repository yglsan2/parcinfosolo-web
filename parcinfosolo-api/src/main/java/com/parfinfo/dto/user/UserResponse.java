package com.parfinfo.dto.user;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String nom;
    private String prenom;
    private String telephone;
    private String service;
    private String poste;
    private Set<String> roles;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private LocalDateTime derniereConnexion;
    private boolean actif;
} 