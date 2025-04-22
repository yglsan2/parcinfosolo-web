package com.parcinfo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OrdinateurPortable extends ObjetNomade {
    
    @NotNull(message = "Le système d'exploitation est obligatoire")
    @NotBlank(message = "Le système d'exploitation ne peut pas être vide")
    private String systemeExploitation;
    
    @NotNull(message = "La taille de l'écran est obligatoire")
    @Min(value = 13, message = "La taille de l'écran doit être d'au moins 13 pouces")
    private Double tailleEcran;
    
    @NotNull(message = "Le processeur est obligatoire")
    @NotBlank(message = "Le processeur ne peut pas être vide")
    private String processeur;
    
    @NotNull(message = "La taille de la RAM est obligatoire")
    @Min(value = 4, message = "La taille de la RAM doit être d'au moins 4 Go")
    private Integer tailleRam;
    
    @NotNull(message = "La taille du stockage est obligatoire")
    @Min(value = 128, message = "La taille du stockage doit être d'au moins 128 Go")
    private Integer tailleStockage;
    
    @NotNull(message = "Le type de stockage est obligatoire")
    @NotBlank(message = "Le type de stockage ne peut pas être vide")
    private String typeStockage;
    
    @NotNull(message = "La carte graphique est obligatoire")
    @NotBlank(message = "La carte graphique ne peut pas être vide")
    private String carteGraphique;
    
    @NotNull(message = "L'autonomie de la batterie est obligatoire")
    @Min(value = 4, message = "L'autonomie de la batterie doit être d'au moins 4 heures")
    private Integer autonomieBatterie;
} 