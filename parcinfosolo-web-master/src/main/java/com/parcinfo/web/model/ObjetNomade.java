package com.parcinfo.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Modèle représentant un objet nomade dans le système.
 * Un objet nomade est un appareil mobile comme un smartphone, une tablette, un ordinateur portable, etc.
 * Cette entité permet de gérer l'inventaire des appareils mobiles et leur attribution aux utilisateurs.
 *
 * @author ParcInfo Team
 * @version 1.0
 */
@Data
@Entity
@Table(name = "objets_nomades")
public class ObjetNomade {
    
    /**
     * Identifiant unique de l'objet nomade.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idObjetNomade;
    
    /**
     * Type d'objet nomade (ex: smartphone, tablette, ordinateur portable).
     * Ne peut pas être vide et doit contenir entre 2 et 50 caractères.
     */
    @NotBlank(message = "Le type est obligatoire")
    @Size(min = 2, max = 50, message = "Le type doit contenir entre 2 et 50 caractères")
    private String type;
    
    /**
     * Numéro de série unique de l'appareil.
     * Ne peut pas être vide et doit contenir entre 5 et 50 caractères.
     */
    @NotBlank(message = "Le numéro de série est obligatoire")
    @Size(min = 5, max = 50, message = "Le numéro de série doit contenir entre 5 et 50 caractères")
    private String numeroSerie;
    
    /**
     * Marque du fabricant de l'appareil.
     * Ne peut pas être vide et doit contenir entre 2 et 50 caractères.
     */
    @NotBlank(message = "La marque est obligatoire")
    @Size(min = 2, max = 50, message = "La marque doit contenir entre 2 et 50 caractères")
    private String marque;
    
    /**
     * Modèle spécifique de l'appareil.
     * Ne peut pas être vide et doit contenir entre 2 et 100 caractères.
     */
    @NotBlank(message = "Le modèle est obligatoire")
    @Size(min = 2, max = 100, message = "Le modèle doit contenir entre 2 et 100 caractères")
    private String modele;
    
    /**
     * État actuel de l'appareil (ex: neuf, utilisé, en panne).
     * Ne peut pas être vide et doit contenir entre 2 et 50 caractères.
     */
    @NotBlank(message = "L'état est obligatoire")
    @Size(min = 2, max = 50, message = "L'état doit contenir entre 2 et 50 caractères")
    private String etat;
    
    /**
     * Système d'exploitation installé sur l'appareil.
     * Ne peut pas être vide et doit contenir entre 2 et 100 caractères.
     */
    @NotBlank(message = "Le système d'exploitation est obligatoire")
    @Size(min = 2, max = 100, message = "Le système d'exploitation doit contenir entre 2 et 100 caractères")
    private String systemeExploitation;
    
    /**
     * Capacité de stockage de l'appareil (ex: 64 Go, 128 Go, 256 Go).
     * Ne peut pas être vide et doit contenir entre 2 et 50 caractères.
     */
    @NotBlank(message = "La capacité de stockage est obligatoire")
    @Size(min = 2, max = 50, message = "La capacité de stockage doit contenir entre 2 et 50 caractères")
    private String capaciteStockage;
    
    /**
     * Personne à qui l'objet nomade est attribué.
     * Relation many-to-one avec l'entité Personne.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personne_id")
    private Personne personne;
    
    /**
     * Date à laquelle l'objet nomade a été ajouté au système.
     * Ne peut pas être null.
     */
    @NotNull(message = "La date d'ajout est obligatoire")
    private LocalDateTime dateAjout;
} 