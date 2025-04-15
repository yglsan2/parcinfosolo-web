package com.parfinfo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ordinateurs")
public class Ordinateur extends Appareil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String statut;

    @Column(nullable = false)
    private String marque;

    @Column(nullable = false)
    private String modele;

    private String numeroSerie;
    private String processeur;
    private String ram;
    private String stockage;
    private String systemeExploitation;
    private String commentaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    private String carteGraphique;
    private String carteReseau;
    private String carteWifi;
    private String carteBluetooth;
    private String batterie;
    private String alimentation;
    private String antivirus;
    private String logiciels;
    private String sauvegardes;
    private String maintenance;
} 