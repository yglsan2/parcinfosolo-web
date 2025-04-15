package com.parfinfo.service;

import com.parfinfo.entity.EtatEquipement;
import com.parfinfo.entity.TypeObjetNomade;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnumerationService {

    public List<String> getTypes() {
        return Arrays.stream(TypeObjetNomade.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public List<String> getEtats() {
        return Arrays.stream(EtatEquipement.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public List<String> getLocalisations() {
        // Pour l'instant, retournons une liste statique de localisations
        // À remplacer par une énumération ou une table de base de données si nécessaire
        return List.of("BUREAU", "SALLE_DE_REUNION", "SALLE_DE_CONFERENCE", "ZONE_COMMUNE", "ENTREPOT");
    }
} 