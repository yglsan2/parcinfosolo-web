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
public class Intervention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    @NotBlank(message = "La description est obligatoire")
    @Column(length = 500)
    private String description;

    @NotNull(message = "Le type d'intervention est obligatoire")
    @Enumerated(EnumType.STRING)
    private TypeIntervention type;

    @ManyToOne
    @JoinColumn(name = "technicien_id")
    private Personne technicien;

    public enum TypeIntervention {
        MAINTENANCE,
        REPARATION,
        MISE_A_JOUR,
        CONFIGURATION,
        AUTRE
    }
} 