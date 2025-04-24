package com.parcinfo.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "objets_nomades")
public class ObjetNomade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idObjetNomade;
    
    @NotBlank(message = "Le type est obligatoire")
    private String type;
    
    @NotBlank(message = "Le numéro de série est obligatoire")
    private String numeroSerie;
    
    @NotBlank(message = "La marque est obligatoire")
    private String marque;
    
    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;
    
    @NotBlank(message = "L'état est obligatoire")
    private String etat;
    
    @NotBlank(message = "Le système d'exploitation est obligatoire")
    private String systemeExploitation;
    
    @NotBlank(message = "La capacité de stockage est obligatoire")
    private String capaciteStockage;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personne_id")
    private Personne personne;
    
    @NotNull(message = "La date d'ajout est obligatoire")
    private LocalDateTime dateAjout;
} 