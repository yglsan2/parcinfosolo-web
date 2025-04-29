package com.parcinfo.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Data
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "appareil_id")
    @NotNull
    private Appareil appareil;
    
    @ManyToOne
    @JoinColumn(name = "personne_id")
    @NotNull
    private Personne personne;
    
    @NotNull
    private LocalDateTime dateDebut;
    
    @NotNull
    private LocalDateTime dateFin;
    
    private String motif;
    
    private String statut;
} 