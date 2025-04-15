package com.parfinfo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "inspection")
public class Inspection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date d'inspection est obligatoire")
    @Column(name = "date_inspection")
    private LocalDateTime dateInspection;

    @NotBlank(message = "Le type d'inspection est obligatoire")
    @Size(max = 50, message = "Le type d'inspection ne doit pas dépasser 50 caractères")
    @Column(name = "type_inspection", length = 50)
    private String typeInspection;

    @NotBlank(message = "Le résultat est obligatoire")
    @Size(max = 500, message = "Le résultat ne doit pas dépasser 500 caractères")
    @Column(length = 500)
    private String resultat;

    @Size(max = 100, message = "L'inspecteur ne doit pas dépasser 100 caractères")
    @Column(length = 100)
    private String inspecteur;

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