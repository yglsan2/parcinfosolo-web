package com.parcinfo.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Modèle représentant une personne dans le système.
 * Une personne peut être un utilisateur, un technicien, un administrateur, etc.
 * Cette entité est utilisée pour gérer les informations des utilisateurs du système
 * et leurs relations avec les équipements informatiques.
 *
 * @author ParcInfo Team
 * @version 1.0
 */
@Data
@Entity
@Table(name = "personnes")
public class Personne {

    /**
     * Identifiant unique de la personne.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersonne;

    /**
     * Nom de famille de la personne.
     * Ne peut pas être vide et doit contenir entre 2 et 100 caractères.
     */
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(nullable = false)
    private String nom;

    /**
     * Prénom de la personne.
     * Ne peut pas être vide et doit contenir entre 2 et 100 caractères.
     */
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 100, message = "Le prénom doit contenir entre 2 et 100 caractères")
    @Column(nullable = false)
    private String prenom;

    /**
     * Adresse email de la personne.
     * Doit être une adresse email valide si renseignée.
     */
    @Email(message = "L'adresse email doit être valide")
    @Column
    private String email;

    /**
     * Numéro de téléphone de la personne.
     * Format libre, mais doit contenir entre 10 et 20 caractères si renseigné.
     */
    @Size(min = 10, max = 20, message = "Le numéro de téléphone doit contenir entre 10 et 20 caractères")
    @Column
    private String telephone;

    /**
     * Fonction ou poste occupé par la personne dans l'organisation.
     * Peut contenir jusqu'à 100 caractères.
     */
    @Size(max = 100, message = "La fonction ne peut pas dépasser 100 caractères")
    @Column
    private String fonction;

    /**
     * Département ou service auquel appartient la personne.
     * Peut contenir jusqu'à 100 caractères.
     */
    @Size(max = 100, message = "Le département ne peut pas dépasser 100 caractères")
    @Column
    private String departement;

    /**
     * Liste des périphériques associés à cette personne.
     * Relation one-to-many avec l'entité Peripherique.
     * Les périphériques sont automatiquement supprimés si la personne est supprimée.
     */
    @OneToMany(mappedBy = "personne", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Peripherique> peripheriques = new ArrayList<>();
} 