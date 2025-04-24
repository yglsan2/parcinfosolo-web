package com.parcinfo.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "ordinateurs")
public class Ordinateur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    
    @NotNull(message = "Le type est obligatoire")
    @Enumerated(EnumType.STRING)
    private TypeOrdinateur type;
    
    @NotNull(message = "L'état est obligatoire")
    @Enumerated(EnumType.STRING)
    private EtatOrdinateur etat;
    
    @NotBlank(message = "Le processeur est obligatoire")
    private String processeur;
    
    @NotBlank(message = "La RAM est obligatoire")
    private String ram;
    
    @NotBlank(message = "Le disque dur est obligatoire")
    private String disqueDur;
    
    private String carteGraphique;
    
    @NotBlank(message = "Le système d'exploitation est obligatoire")
    private String systemeExploitation;
    
    @NotNull(message = "La date d'ajout est obligatoire")
    @PastOrPresent(message = "La date d'ajout ne peut pas être dans le futur")
    private LocalDate dateAjout;
    
    private LocalDate derniereMaintenance;
    
    public enum TypeOrdinateur {
        FIXE, PORTABLE
    }
    
    public enum EtatOrdinateur {
        DISPONIBLE, EN_PANNE, EN_MAINTENANCE
    }
} 