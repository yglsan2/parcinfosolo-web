package com.parfinfo.dto.geo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGeoRequest {
    @NotBlank(message = "Le type est obligatoire")
    private String type;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String description;

    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    @NotBlank(message = "Le code postal est obligatoire")
    private String codePostal;

    @NotBlank(message = "Le pays est obligatoire")
    private String pays;

    @NotNull(message = "La latitude est obligatoire")
    private Double latitude;

    @NotNull(message = "La longitude est obligatoire")
    private Double longitude;

    private String batiment;
    private String etage;
    private String salle;
    private String localisation;
} 