package com.parcinfo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersonne;
    
    @Column(length = 30)
    private String nom;
    
    @Column(length = 25)
    private String prenom;
    
    @Column(length = 50)
    private String adresse;
    
    @Column(length = 15)
    private String telephone;
    
    private LocalDate dateNaissance;
    
    @OneToMany(mappedBy = "personne", cascade = CascadeType.ALL)
    private List<Affectation> affectations = new ArrayList<>();
} 