package com.parcinfo.validation;

import com.parcinfo.model.Tablette;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Validateur spécifique pour les tablettes.
 * Les validations de base sont gérées par les annotations de validation et le validateur de base.
 * Ce validateur ajoute des validations spécifiques aux tablettes.
 */
@Component
public class TabletteValidator extends ObjetNomadeValidator {

    /**
     * Valide une tablette en combinant les validations de base et les validations spécifiques.
     * 
     * @param tablette La tablette à valider
     * @return Liste des messages d'erreur, vide si la tablette est valide
     */
    public List<String> validate(Tablette tablette) {
        List<String> errors = new ArrayList<>();
        
        if (tablette == null) {
            errors.add("La tablette ne peut pas être null");
            return errors;
        }

        // Validations spécifiques aux tablettes
        if (tablette.getTailleEcran() != null && tablette.getTailleEcran() < 8.0) {
            errors.add("La taille de l'écran d'une tablette doit être d'au moins 8 pouces");
        }

        if (tablette.getCapaciteBatterie() != null && tablette.getCapaciteBatterie() < 5000) {
            errors.add("La capacité de la batterie doit être d'au moins 5000 mAh");
        }

        // Vérification de la cohérence des fonctionnalités
        if (Boolean.TRUE.equals(tablette.getSupportClavier()) && tablette.getTailleEcran() != null 
            && tablette.getTailleEcran() < 10.0) {
            errors.add("Une tablette avec support clavier doit avoir un écran d'au moins 10 pouces");
        }

        // Vérification de la RAM minimale pour les tablettes
        if (tablette.getTailleRam() != null && tablette.getTailleRam() < 2) {
            errors.add("Une tablette doit avoir au moins 2 Go de RAM");
        }

        // Vérification du stockage minimal pour les tablettes
        if (tablette.getTailleStockage() != null && tablette.getTailleStockage() < 16) {
            errors.add("Une tablette doit avoir au moins 16 Go de stockage");
        }

        // Vérification pour les tablettes avec support stylet
        if (Boolean.TRUE.equals(tablette.getSupportStylet()) && tablette.getTailleEcran() != null 
            && tablette.getTailleEcran() < 9.0) {
            errors.add("Une tablette avec support stylet doit avoir un écran d'au moins 9 pouces");
        }

        return errors;
    }
} 