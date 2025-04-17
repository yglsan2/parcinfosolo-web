package com.parcinfo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private Long idObjetNomade;

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
    
    private String systemeExploitation;
    private String versionSysteme;
    
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