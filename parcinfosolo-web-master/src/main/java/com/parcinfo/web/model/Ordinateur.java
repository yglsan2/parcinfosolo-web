package com.parcinfo.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Modèle représentant un ordinateur dans le système.
 * Cette entité gère les informations relatives aux ordinateurs du parc informatique,
 * qu'ils soient fixes ou portables.
 *
 * @author ParcInfo Team
 * @version 1.0
 */
@Data
@Entity
@Table(name = "ordinateurs")
public class Ordinateur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(nullable = false)
    private String nom;
    
    @NotNull(message = "Le type est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeOrdinateur type;
    
    @NotNull(message = "L'état est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatOrdinateur etat;
    
    @NotBlank(message = "Le processeur est obligatoire")
    @Size(min = 2, max = 100, message = "Le processeur doit contenir entre 2 et 100 caractères")
    @Column(nullable = false)
    private String processeur;
    
    @NotBlank(message = "La RAM est obligatoire")
    @Size(min = 2, max = 50, message = "La RAM doit contenir entre 2 et 50 caractères")
    @Column(nullable = false)
    private String ram;
    
    @NotBlank(message = "Le disque dur est obligatoire")
    @Size(min = 2, max = 100, message = "Le disque dur doit contenir entre 2 et 100 caractères")
    @Column(nullable = false)
    private String disqueDur;
    
    @Size(max = 100, message = "La carte graphique ne peut pas dépasser 100 caractères")
    @Column
    private String carteGraphique;
    
    @NotBlank(message = "Le système d'exploitation est obligatoire")
    @Size(min = 2, max = 100, message = "Le système d'exploitation doit contenir entre 2 et 100 caractères")
    @Column(nullable = false)
    private String systemeExploitation;
    
    @NotNull(message = "La date d'ajout est obligatoire")
    @PastOrPresent(message = "La date d'ajout ne peut pas être dans le futur")
    @Column(nullable = false)
    private LocalDateTime dateAjout;
    
    @Column
    private LocalDateTime derniereMaintenance;
    
    /**
     * Types d'ordinateurs disponibles dans le système.
     */
    public enum TypeOrdinateur {
        FIXE, PORTABLE
    }
    
    /**
     * États possibles d'un ordinateur.
     */
    public enum EtatOrdinateur {
        DISPONIBLE, EN_PANNE, EN_MAINTENANCE
    }
} 