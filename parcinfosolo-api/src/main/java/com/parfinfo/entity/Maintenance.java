package com.parfinfo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenances")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateMaintenance;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String description;

    private String resultat;

    @ManyToOne
    @JoinColumn(name = "ordinateur_id")
    private Ordinateur ordinateur;

    @ManyToOne
    @JoinColumn(name = "technicien_id")
    private User technicien;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;
} 