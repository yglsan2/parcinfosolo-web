package com.parfinfo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ordinateurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ordinateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroSerie;

    @Column(nullable = false)
    private String marque;

    @Column(nullable = false)
    private String modele;

    private String processeur;
    private String ram;
    private String stockage;
    private String systemeExploitation;

    @Enumerated(EnumType.STRING)
    private EtatEquipement etat;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private User utilisateur;

    @ManyToMany
    @JoinTable(
        name = "ordinateur_tags",
        joinColumns = @JoinColumn(name = "ordinateur_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "ordinateur", cascade = CascadeType.ALL)
    private Set<Maintenance> maintenances = new HashSet<>();

    @OneToMany(mappedBy = "ordinateur", cascade = CascadeType.ALL)
    private Set<Peripherique> peripheriques = new HashSet<>();
} 