package com.parfinfo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "appareil_standard")
public class AppareilStandard extends Appareil {
    // Cette classe représente un appareil générique
    // Elle hérite de toutes les propriétés de la classe Appareil
    
    @NotBlank(message = "Le modèle est obligatoire")
    @Size(max = 50, message = "Le modèle ne doit pas dépasser 50 caractères")
    @Column(length = 50)
    private String modele;
    
    @NotBlank(message = "Le numéro de série est obligatoire")
    @Size(max = 50, message = "Le numéro de série ne doit pas dépasser 50 caractères")
    @Column(name = "numero_serie", length = 50, unique = true)
    private String numeroSerie;
    
    @NotNull(message = "L'état est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EtatAppareil etat;
    
    @NotNull(message = "Le type est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TypeAppareil type;

    @Column(name = "date_acquisition")
    private LocalDateTime dateAcquisition;

    @Column(name = "date_derniere_maintenance")
    private LocalDateTime dateDerniereMaintenance;

    @Column(name = "date_prochaine_maintenance")
    private LocalDateTime dateProchaineMaintenance;

    @Size(max = 500, message = "Les notes ne doivent pas dépasser 500 caractères")
    @Column(length = 500)
    private String notes;

    @Column(name = "cout_acquisition")
    private Double coutAcquisition;

    @Column(name = "cout_maintenance")
    private Double coutMaintenance;

    @Column(name = "garantie_jours")
    private Integer garantieJours;

    @Column(name = "date_fin_garantie")
    private LocalDateTime dateFinGarantie;

    @Column(name = "fournisseur")
    @Size(max = 100, message = "Le nom du fournisseur ne doit pas dépasser 100 caractères")
    private String fournisseur;

    @Column(name = "numero_commande")
    @Size(max = 50, message = "Le numéro de commande ne doit pas dépasser 50 caractères")
    private String numeroCommande;

    @Column(name = "est_actif")
    private Boolean estActif = true;

    @Column(name = "emplacement")
    @Size(max = 100, message = "L'emplacement ne doit pas dépasser 100 caractères")
    private String emplacement;

    @Column(name = "responsable")
    @Size(max = 100, message = "Le nom du responsable ne doit pas dépasser 100 caractères")
    private String responsable;

    @Column(name = "numero_inventaire")
    @Size(max = 50, message = "Le numéro d'inventaire ne doit pas dépasser 50 caractères")
    private String numeroInventaire;

    @Column(name = "date_derniere_inspection")
    private LocalDateTime dateDerniereInspection;

    @Column(name = "date_prochaine_inspection")
    private LocalDateTime dateProchaineInspection;

    @Column(name = "niveau_risque")
    @Enumerated(EnumType.STRING)
    private NiveauRisque niveauRisque = NiveauRisque.FAIBLE;

    @OneToMany(mappedBy = "appareilStandard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Maintenance> maintenances = new HashSet<>();

    @OneToMany(mappedBy = "appareilStandard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Inspection> inspections = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        dateAcquisition = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        if (dateDerniereMaintenance != null) {
            dateProchaineMaintenance = dateDerniereMaintenance.plusMonths(6);
        }
        if (dateDerniereInspection != null) {
            dateProchaineInspection = dateDerniereInspection.plusMonths(3);
        }
    }

    public enum NiveauRisque {
        FAIBLE,
        MOYEN,
        ELEVE,
        CRITIQUE
    }
} 