package com.parfinfo.dto.objetnomade;

import com.parfinfo.entity.EtatEquipement;
import com.parfinfo.entity.TypeObjetNomade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateObjetNomadeRequest {
    @NotNull(message = "Le type est obligatoire")
    private TypeObjetNomade type;

    @NotNull(message = "Le statut est obligatoire")
    private EtatEquipement statut;

    @NotBlank(message = "La marque est obligatoire")
    private String marque;

    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;

    @NotBlank(message = "Le numéro de série est obligatoire")
    private String numeroSerie;

    private String commentaire;
} 