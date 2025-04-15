package com.parfinfo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "maintenance")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de maintenance est obligatoire")
    @Column(name = "date_maintenance")
    private LocalDateTime dateMaintenance;

    @NotBlank(message = "Le type de maintenance est obligatoire")
    @Size(max = 50, message = "Le type de maintenance ne doit pas dépasser 50 caractères")
    @Column(name = "type_maintenance", length = 50)
    private String typeMaintenance;

    @NotBlank(message = "La description est obligatoire")
    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    @Column(length = 500)
    private String description;

    @Column(name = "cout_maintenance")
    private Double coutMaintenance;

    @Size(max = 100, message = "Le technicien ne doit pas dépasser 100 caractères")
    @Column(length = 100)
    private String technicien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appareil_standard_id")
    private AppareilStandard appareilStandard;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
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