package com.parfinfo.dto.peripherique;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PeripheriqueCreateRequest {
    
    @NotBlank(message = "Le type est obligatoire")
    private String type;
    
    @NotBlank(message = "La marque est obligatoire")
    private String marque;
    
    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;
    
    @NotBlank(message = "Le numéro de série est obligatoire")
    private String numeroSerie;
    
    @NotBlank(message = "Le statut est obligatoire")
    private String statut;
    
    private String description;
    
    @NotNull(message = "L'ID de l'appareil est obligatoire")
    private Long appareilId;
} 