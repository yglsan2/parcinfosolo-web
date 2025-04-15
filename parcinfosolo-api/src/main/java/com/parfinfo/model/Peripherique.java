package com.parfinfo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "peripheriques")
public class Peripherique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String marque;

    @Column(nullable = false)
    private String modele;

    @Column(nullable = false)
    private String statut;

    private String numeroSerie;

    @Column(length = 1000)
    private String description;

    private LocalDateTime dateAcquisition;

    private LocalDateTime dateDerniereMaintenance;

    @Column(length = 1000)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "appareil_id")
    private Appareil appareil;

    @Column(nullable = false)
    private LocalDateTime dateCreation;

    @Column(nullable = false)
    private LocalDateTime dateModification;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateModification = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }
} 