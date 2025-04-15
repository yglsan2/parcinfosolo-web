package com.parfinfo.dto.geo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoResponse {
    private Long id;
    private String type;
    private String nom;
    private String description;
    private String adresse;
    private String ville;
    private String codePostal;
    private String pays;
    private Double latitude;
    private Double longitude;
    private String batiment;
    private String etage;
    private String salle;
    private String localisation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 