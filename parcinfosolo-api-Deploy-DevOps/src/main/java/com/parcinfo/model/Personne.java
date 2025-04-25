package com.parcinfo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Column(length = 30)
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Column(length = 25)
    private String prenom;
    
    @NotBlank(message = "L'adresse est obligatoire")
    @Column(length = 50)
    private String adresse;
    
    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Column(length = 15)
    private String telephone;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email n'est pas valide")
    @Column(unique = true)
    private String email;
    
    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateNaissance;
    
    @OneToMany(mappedBy = "personne", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Affectation> affectations = new ArrayList<>();

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
} 