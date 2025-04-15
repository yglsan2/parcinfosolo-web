package com.parfinfo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "peripheriques")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Peripherique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String marque;

    @Column(nullable = false)
    private String modele;

    @Column(nullable = false)
    private String numeroSerie;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypePeripherique type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatEquipement etat;

    @Column
    private String description;

    @Column(nullable = false)
    private String localisation;

    @Column(name = "date_acquisition")
    private java.time.LocalDate dateAcquisition;

    @Column(name = "derniere_maintenance")
    private java.time.LocalDate derniereMaintenance;

    @ManyToOne
    @JoinColumn(name = "ordinateur_id")
    private Ordinateur ordinateur;

    @Column
    private String emplacement;

    @Column
    private String fabricant;

    @Column
    private String versionFirmware;

    @Column
    private String versionDriver;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;
} 