package com.parfinfo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "appareil", indexes = {
    @Index(name = "idx_appareil_numero_serie", columnList = "numero_serie"),
    @Index(name = "idx_appareil_numero_inventaire", columnList = "numero_inventaire"),
    @Index(name = "idx_appareil_statut", columnList = "statut"),
    @Index(name = "idx_appareil_type", columnList = "type")
})
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Appareil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    @Column(length = 100)
    private String nom;

    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    @Column(length = 500)
    private String description;

    @NotNull(message = "Le statut est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatutAppareil statut = StatutAppareil.DISPONIBLE;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @Column(name = "date_derniere_utilisation")
    private LocalDateTime dateDerniereUtilisation;

    @PositiveOrZero(message = "La durée de vie estimée doit être positive ou nulle")
    @Column(name = "duree_vie_estimee_mois")
    private Integer dureeVieEstimeeMois;

    @DecimalMin(value = "0.0", message = "Le coût horaire d'utilisation doit être positif ou nul")
    @Column(name = "cout_horaire_utilisation", precision = 10, scale = 2)
    private BigDecimal coutHoraireUtilisation;

    @Column(name = "niveau_utilisation")
    @Enumerated(EnumType.STRING)
    private NiveauUtilisation niveauUtilisation = NiveauUtilisation.NORMAL;

    @Size(max = 1000, message = "Les spécifications techniques ne doivent pas dépasser 1000 caractères")
    @Column(name = "specifications_techniques", length = 1000)
    private String specificationsTechniques;

    @Column(name = "est_archive")
    private Boolean estArchive = false;

    @Column(name = "date_archivage")
    private LocalDateTime dateArchivage;

    @Size(max = 500, message = "La raison d'archivage ne doit pas dépasser 500 caractères")
    @Column(name = "raison_archivage", length = 500)
    private String raisonArchivage;

    @ManyToMany
    @JoinTable(
        name = "affectation",
        joinColumns = @JoinColumn(name = "id_appareil"),
        inverseJoinColumns = @JoinColumn(name = "id_personne")
    )
    private Set<Personne> personnes = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeAppareil type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatAppareil etat = EtatAppareil.NEUF;

    @Column(name = "numero_serie")
    @Size(max = 50, message = "Le numéro de série ne doit pas dépasser 50 caractères")
    private String numeroSerie;

    @Column(name = "numero_inventaire")
    @Size(max = 50, message = "Le numéro d'inventaire ne doit pas dépasser 50 caractères")
    private String numeroInventaire;

    @Column(name = "date_acquisition")
    private LocalDateTime dateAcquisition;

    @Column(name = "date_fin_garantie")
    private LocalDateTime dateFinGarantie;

    @DecimalMin(value = "0.0", message = "Le coût d'acquisition doit être positif ou nul")
    @Column(name = "cout_acquisition", precision = 10, scale = 2)
    private BigDecimal coutAcquisition;

    @Column(name = "fournisseur")
    @Size(max = 100, message = "Le fournisseur ne doit pas dépasser 100 caractères")
    private String fournisseur;

    @Column(name = "numero_commande")
    @Size(max = 50, message = "Le numéro de commande ne doit pas dépasser 50 caractères")
    private String numeroCommande;

    @Column(name = "emplacement")
    @Size(max = 100, message = "L'emplacement ne doit pas dépasser 100 caractères")
    private String emplacement;

    @Column(name = "responsable")
    @Size(max = 100, message = "Le responsable ne doit pas dépasser 100 caractères")
    private String responsable;

    @Column(name = "date_derniere_maintenance")
    private LocalDateTime dateDerniereMaintenance;

    @Column(name = "date_prochaine_maintenance")
    private LocalDateTime dateProchaineMaintenance;

    @Column(name = "notes")
    @Size(max = 1000, message = "Les notes ne doivent pas dépasser 1000 caractères")
    private String notes;

    @Column(name = "cout_total_maintenance", precision = 10, scale = 2)
    private BigDecimal coutTotalMaintenance = BigDecimal.ZERO;

    @Column(name = "heures_utilisation")
    private Integer heuresUtilisation = 0;

    @Column(name = "frequence_maintenance_mois")
    private Integer frequenceMaintenanceMois;

    @Column(name = "derniere_inspection")
    private LocalDateTime derniereInspection;

    @Column(name = "prochaine_inspection")
    private LocalDateTime prochaineInspection;

    @Column(name = "niveau_risque")
    @Enumerated(EnumType.STRING)
    private NiveauRisque niveauRisque = NiveauRisque.FAIBLE;

    @Column(name = "documentation_url")
    @Size(max = 255, message = "L'URL de documentation ne doit pas dépasser 255 caractères")
    private String documentationUrl;

    @Column(name = "fabricant")
    @Size(max = 100, message = "Le fabricant ne doit pas dépasser 100 caractères")
    private String fabricant;

    @Column(name = "modele")
    @Size(max = 100, message = "Le modèle ne doit pas dépasser 100 caractères")
    private String modele;

    @Column(name = "version_firmware")
    @Size(max = 50, message = "La version du firmware ne doit pas dépasser 50 caractères")
    private String versionFirmware;

    @Column(name = "date_mise_a_jour_firmware")
    private LocalDateTime dateMiseAJourFirmware;

    @Column(name = "est_connecte")
    private Boolean estConnecte = false;

    @Column(name = "adresse_ip")
    @Size(max = 45, message = "L'adresse IP ne doit pas dépasser 45 caractères")
    private String adresseIp;

    @Column(name = "port")
    private Integer port;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateModification = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }

    public enum StatutAppareil {
        DISPONIBLE,
        EN_UTILISATION,
        EN_MAINTENANCE,
        EN_PANNE,
        HORS_SERVICE
    }

    public enum NiveauUtilisation {
        FAIBLE,
        NORMAL,
        ELEVE,
        INTENSIF
    }

    public enum NiveauRisque {
        FAIBLE,
        MOYEN,
        ELEVE,
        CRITIQUE
    }
} 