package com.parcinfo.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Modèle représentant un périphérique dans le système.
 * Un périphérique peut être une imprimante, un scanner, ou tout autre équipement périphérique.
 *
 * @author ParcInfo Team
 * @version 1.0
 */
@Data
@Entity
@Table(name = "peripheriques")
public class Peripherique {

    /**
     * Identifiant unique du périphérique.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom du périphérique.
     * Ne peut pas être null ou vide.
     */
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(nullable = false)
    private String nom;

    /**
     * Type de périphérique (clavier, souris, écran, etc.).
     * Ne peut pas être null ou vide.
     */
    @NotBlank(message = "Le type est obligatoire")
    @Size(min = 2, max = 50, message = "Le type doit contenir entre 2 et 50 caractères")
    @Column(nullable = false)
    private String type;

    /**
     * Description du périphérique.
     * Peut être null.
     */
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    @Column
    private String description;

    /**
     * Numéro de série du périphérique.
     * Peut être null.
     */
    @NotBlank(message = "Le numéro de série est obligatoire")
    @Size(min = 5, max = 50, message = "Le numéro de série doit contenir entre 5 et 50 caractères")
    @Column(nullable = false)
    private String numeroSerie;

    /**
     * Marque du périphérique.
     * Peut être null.
     */
    @Size(min = 2, max = 50, message = "La marque doit contenir entre 2 et 50 caractères")
    @Column
    private String marque;

    /**
     * Modèle du périphérique.
     * Peut être null.
     */
    @Size(min = 2, max = 100, message = "Le modèle doit contenir entre 2 et 100 caractères")
    @Column
    private String modele;

    /**
     * État du périphérique (fonctionnel, en panne, etc.).
     * Ne peut pas être null ou vide.
     */
    @NotBlank(message = "L'état est obligatoire")
    @Size(min = 2, max = 50, message = "L'état doit contenir entre 2 et 50 caractères")
    @Column(nullable = false)
    private String etat;

    /**
     * Appareil auquel le périphérique est connecté.
     * Relation Many-to-One avec l'entité Appareil.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personne_id", nullable = true)
    private Personne personne;

    /**
     * Localisation du périphérique.
     * Peut être null.
     */
    @Size(max = 200, message = "La localisation ne peut pas dépasser 200 caractères")
    @Column
    private String localisation;

    /**
     * Date d'acquisition du périphérique.
     * Peut être null.
     */
    @NotNull(message = "La date d'acquisition est obligatoire")
    @Column(nullable = false)
    private LocalDateTime dateAcquisition;

    /**
     * Date de la dernière maintenance du périphérique.
     * Peut être null.
     */
    @Column
    private LocalDateTime derniereMaintenance;

    /**
     * Commentaires sur le périphérique.
     * Peut être null.
     */
    @Size(max = 1000, message = "Les commentaires ne peuvent pas dépasser 1000 caractères")
    @Column(length = 1000)
    private String commentaires;

    @OneToMany(mappedBy = "peripherique", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Intervention> interventions = new ArrayList<>();
} 