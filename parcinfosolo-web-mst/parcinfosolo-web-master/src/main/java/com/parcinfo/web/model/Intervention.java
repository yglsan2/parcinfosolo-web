package com.parcinfo.web.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "interventions")
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date est obligatoire")
    private LocalDateTime date;

    @NotBlank(message = "Le type d'intervention est obligatoire")
    @Enumerated(EnumType.STRING)
    private TypeIntervention type;

    @NotBlank(message = "La description est obligatoire")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Le technicien est obligatoire")
    private String technicien;

    private Integer duree;

    @NotNull(message = "Le résultat est obligatoire")
    @Enumerated(EnumType.STRING)
    private ResultatIntervention resultat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "peripherique_id", nullable = false)
    private Peripherique peripherique;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TypeIntervention getType() {
        return type;
    }

    public void setType(TypeIntervention type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnicien() {
        return technicien;
    }

    public void setTechnicien(String technicien) {
        this.technicien = technicien;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public ResultatIntervention getResultat() {
        return resultat;
    }

    public void setResultat(ResultatIntervention resultat) {
        this.resultat = resultat;
    }

    public Peripherique getPeripherique() {
        return peripherique;
    }

    public void setPeripherique(Peripherique peripherique) {
        this.peripherique = peripherique;
    }
} 