package com.parcinfo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
public abstract class Appareil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAppareil;
    
    @Column(length = 25)
    private String marque;
    
    @Column(length = 30)
    private String libelle;
    
    @OneToMany(mappedBy = "appareil", cascade = CascadeType.ALL)
    private List<Affectation> affectations = new ArrayList<>();
} 