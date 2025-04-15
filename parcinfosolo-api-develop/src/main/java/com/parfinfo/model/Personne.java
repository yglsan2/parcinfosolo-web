package com.parfinfo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import lombok.Data;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "personne")
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersonne;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(nullable = false)
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Column(nullable = false)
    private String prenom;

    @Email(message = "L'email doit être valide")
    @Column(unique = true)
    private String email;

    private String adresse;

    @Pattern(regexp = "^[0-9]{10}$", message = "Le numéro de téléphone doit contenir 10 chiffres")
    @Column(length = 10)
    private String telephone;

    @Past(message = "La date de naissance doit être dans le passé")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    @ManyToMany
    @JoinTable(
        name = "affectation",
        joinColumns = @JoinColumn(name = "id_personne"),
        inverseJoinColumns = @JoinColumn(name = "id_appareil")
    )
    private Set<Appareil> appareils = new HashSet<>();
} 