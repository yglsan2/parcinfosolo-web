package com.parfinfo.dto.objetnomade;

import com.parfinfo.entity.EtatEquipement;
import com.parfinfo.entity.TypeObjetNomade;
import lombok.Data;

@Data
public class UpdateObjetNomadeRequest {
    private TypeObjetNomade type;
    private EtatEquipement statut;
    private String marque;
    private String modele;
    private String numeroSerie;
    private String commentaire;
} 