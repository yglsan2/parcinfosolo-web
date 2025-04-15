package com.parfinfo.dto.objetnomade;

import com.parfinfo.entity.EtatEquipement;
import com.parfinfo.entity.TypeObjetNomade;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ObjetNomadeResponse {
    private Long id;
    private TypeObjetNomade type;
    private EtatEquipement statut;
    private String marque;
    private String modele;
    private String numeroSerie;
    private String commentaire;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
} 