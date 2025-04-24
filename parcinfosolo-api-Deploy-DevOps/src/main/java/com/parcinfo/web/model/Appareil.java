package com.parcinfo.web.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "appareil")
public class Appareil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String type;

    @Column(name = "numero_serie")
    private String numeroSerie;

    @Column
    private String description;
} 