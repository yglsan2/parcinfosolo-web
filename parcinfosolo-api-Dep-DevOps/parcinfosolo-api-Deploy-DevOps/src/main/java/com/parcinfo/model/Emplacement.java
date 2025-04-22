package com.parcinfo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Emplacement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmplacement;
    
    @Column(length = 30)
    private String numero;
    
    @ManyToOne
    @JoinColumn(name = "id_parc")
    private Parc parc;
    
    @ManyToOne
    @JoinColumn(name = "id_type_emplacement")
    private TypeEmplacement typeEmplacement;
} 