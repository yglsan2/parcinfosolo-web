package com.parcinfo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Affectation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_personne")
    private Personne personne;
    
    @ManyToOne
    @JoinColumn(name = "id_appareil")
    private Appareil appareil;
    
    private LocalDate dateAffectation;
} 