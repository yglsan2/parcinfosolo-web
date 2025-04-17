package com.parcinfo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Peripherique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPeripherique;
    
    @NotNull(message = "Le type est obligatoire")
    @Enumerated(EnumType.STRING)
    private TypePeripherique type;
    
    @NotBlank(message = "La marque est obligatoire")
    private String marque;
    
    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;
    
    @NotBlank(message = "Le numéro de série est obligatoire")
    @Column(unique = true)
    private String numeroSerie;
    
    @NotNull(message = "La date d'acquisition est obligatoire")
    private LocalDateTime dateAcquisition;
    
    private LocalDateTime dateMiseEnService;
    
    @Enumerated(EnumType.STRING)
    private EtatPeripherique etat;
    
    private String commentaire;
    
    private boolean estActif = true;
    
    @ManyToOne
    private Ordinateur ordinateur;
    
    @ManyToOne
    private ObjetNomade objetNomade;
    
    @PrePersist
    @PreUpdate
    private void validateAssociations() {
        if (ordinateur != null && objetNomade != null) {
            throw new IllegalStateException("Un périphérique ne peut être associé qu'à un seul appareil");
        }
    }
    
    public enum EtatPeripherique {
        NEUF,
        BON,
        MOYEN,
        MAUVAIS,
        HORS_SERVICE
    }
} 