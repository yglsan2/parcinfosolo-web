package com.parfinfo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appareils")
public class Appareil {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String type;
    
    @Column(nullable = false)
    private String marque;
    
    @Column(nullable = false)
    private String modele;
    
    @Column(name = "numero_serie", nullable = false, unique = true)
    private String numeroSerie;
    
    @Column(nullable = false)
    private String status;
    
    private String localisation;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "date_acquisition", nullable = false)
    private LocalDateTime dateAcquisition;
    
    @Column(name = "date_derniere_maintenance")
    private LocalDateTime dateDerniereMaintenance;
    
    @Column(name = "date_prochaine_maintenance")
    private LocalDateTime dateProchaineMaintenance;
    
    @Column(nullable = false)
    private String fournisseur;
    
    @Column(name = "cout_acquisition", nullable = false)
    private Double coutAcquisition;
    
    private String garantie;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }
} 