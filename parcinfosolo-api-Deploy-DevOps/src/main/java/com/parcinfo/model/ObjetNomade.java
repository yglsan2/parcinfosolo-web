package com.parcinfo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "objets_nomades")
public class ObjetNomade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotNull(message = "Le type est obligatoire")
    @Enumerated(EnumType.STRING)
    private TypeObjetNomade type;

    @NotBlank(message = "Le numéro de série est obligatoire")
    @Column(unique = true)
    private String numeroSerie;

    @NotBlank(message = "La marque est obligatoire")
    private String marque;

    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;

    @Enumerated(EnumType.STRING)
    private EtatObjetNomade etat;

    @NotNull(message = "La date d'acquisition est obligatoire")
    private LocalDateTime dateAcquisition;

    private LocalDateTime dateMiseEnService;
    private LocalDateTime dateDerniereMaintenance;
    
    @NotBlank(message = "Le système d'exploitation est obligatoire")
    private String systemeExploitation;
    private String versionSysteme;
    
    @Positive(message = "La taille de l'écran doit être positive")
    private Double tailleEcran;
    
    @Positive(message = "La capacité de la batterie doit être positive")
    private Integer capaciteBatterie;
    
    @NotBlank(message = "La résolution de la caméra est obligatoire")
    private String resolutionCamera;
    
    @Positive(message = "La taille du stockage doit être positive")
    private Integer tailleStockage;
    
    @Positive(message = "La taille de la RAM doit être positive")
    private Integer tailleRam;
    
    @NotBlank(message = "Le processeur est obligatoire")
    private String processeur;
    
    private boolean estActif = true;
    private String commentaire;

    @OneToMany(mappedBy = "objetNomade", cascade = CascadeType.ALL)
    private List<Peripherique> peripheriques = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "personne_id")
    private Personne utilisateur;

    public enum EtatObjetNomade {
        NEUF,
        BON,
        MOYEN,
        MAUVAIS,
        HORS_SERVICE
    }
} 