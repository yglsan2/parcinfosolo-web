package com.parcinfo.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Modèle représentant une intervention technique sur un périphérique.
 * Cette entité permet de suivre toutes les interventions de maintenance,
 * réparation ou mise à jour effectuées sur les périphériques.
 *
 * @author ParcInfo Team
 * @version 1.0
 */
@Data
@Entity
@Table(name = "interventions")
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le périphérique est obligatoire")
    @ManyToOne
    @JoinColumn(name = "peripherique_id", nullable = false)
    private Peripherique peripherique;

    @NotNull(message = "La date est obligatoire")
    @Column(nullable = false)
    private LocalDateTime date;

    @NotBlank(message = "Le nom du technicien est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom du technicien doit contenir entre 2 et 100 caractères")
    @Column(nullable = false)
    private String technicien;

    @NotNull(message = "La durée est obligatoire")
    @Min(value = 1, message = "La durée minimum est de 1 minute")
    @Column(nullable = false)
    private Integer duree;

    @Size(max = 1000, message = "La description ne peut pas dépasser 1000 caractères")
    @Column(length = 1000)
    private String description;

    @NotBlank(message = "Le statut est obligatoire")
    @Column(nullable = false)
    private String statut;

    @NotBlank(message = "Le type est obligatoire")
    @Column(nullable = false)
    private String type;

    @Size(max = 1000, message = "Le résultat ne peut pas dépasser 1000 caractères")
    @Column(length = 1000)
    private String resultat;
} 