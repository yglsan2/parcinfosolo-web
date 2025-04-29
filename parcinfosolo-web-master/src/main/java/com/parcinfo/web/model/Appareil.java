package com.parcinfo.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Entité représentant un appareil dans le système.
 * Cette classe gère les informations relatives aux appareils du parc informatique,
 * tels que les ordinateurs, les périphériques, etc.
 *
 * @author ParcInfo Team
 * @version 1.0
 */
@Data
@Entity
@Table(name = "appareils")
public class Appareil {

    /**
     * Identifiant unique de l'appareil.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom de l'appareil.
     * Ne peut pas être vide et doit contenir entre 2 et 100 caractères.
     */
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;

    /**
     * Description détaillée de l'appareil.
     * Peut contenir jusqu'à 500 caractères.
     */
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    /**
     * Type d'appareil (ex: ordinateur, imprimante, scanner, etc.).
     * Ne peut pas être vide et doit contenir entre 2 et 50 caractères.
     */
    @NotBlank(message = "Le type est obligatoire")
    @Size(min = 2, max = 50, message = "Le type doit contenir entre 2 et 50 caractères")
    private String type;

    /**
     * État actuel de l'appareil (ex: en service, en panne, en maintenance).
     * Ne peut pas être vide et doit contenir entre 2 et 50 caractères.
     */
    @NotBlank(message = "L'état est obligatoire")
    @Size(min = 2, max = 50, message = "L'état doit contenir entre 2 et 50 caractères")
    private String etat;
} 