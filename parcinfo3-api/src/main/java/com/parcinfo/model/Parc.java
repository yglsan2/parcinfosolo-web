package com.parcinfo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Parc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParc;
    
    @Column(length = 50)
    private String nom;
    
    @Column(length = 100)
    private String adresse;
    
    @OneToMany(mappedBy = "parc", cascade = CascadeType.ALL)
    private List<Emplacement> emplacements = new ArrayList<>();
} 