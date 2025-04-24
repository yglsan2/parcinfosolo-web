package com.parcinfo.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "peripheriques")
public class Peripherique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPeripherique;

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

    private String connexion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordinateur_id")
    private Ordinateur ordinateur;

    @NotNull(message = "La date d'ajout est obligatoire")
    private LocalDateTime dateAjout;

    @OneToMany(mappedBy = "peripherique", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Intervention> interventions = new ArrayList<>();
} 