package com.parcinfo.web.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "interventions_objets_nomades")
public class InterventionObjetNomade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "objet_nomade_id", nullable = false)
    private ObjetNomade objetNomade;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String description;

    @Column(name = "date_intervention", nullable = false)
    private LocalDateTime dateIntervention;

    @Column(name = "date_fin_intervention")
    private LocalDateTime dateFinIntervention;

    @Column(nullable = false)
    private String statut;

    @Column(name = "cout_intervention")
    private Double coutIntervention;

    @Column(columnDefinition = "TEXT")
    private String commentaires;
} 