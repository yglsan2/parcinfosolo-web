package com.parcinfo.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "interventions")
public class Intervention {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "La date est obligatoire")
    private String date;
    
    @NotBlank(message = "Le type est obligatoire")
    @Enumerated(EnumType.STRING)
    private String type;
    
    @NotBlank(message = "La description est obligatoire")
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotBlank(message = "Le technicien est obligatoire")
    private String technicien;
    
    private String commentaire;
    
    @NotNull(message = "Le résultat est obligatoire")
    @Enumerated(EnumType.STRING)
    private String resultat;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appareil_id")
    private Appareil appareil;
} 