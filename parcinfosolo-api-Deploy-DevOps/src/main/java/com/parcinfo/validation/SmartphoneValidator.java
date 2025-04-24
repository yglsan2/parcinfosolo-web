package com.parcinfo.validation;

import com.parcinfo.model.Smartphone;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validateur spécifique pour les smartphones.
 * Les validations de base sont gérées par les annotations de validation et le validateur de base.
 * Ce validateur ajoute des validations spécifiques aux smartphones.
 */
@Component
public class SmartphoneValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Smartphone.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Smartphone smartphone = (Smartphone) target;

        // Validation de la taille de l'écran
        if (smartphone.getTailleEcran() != null) {
            if (smartphone.getTailleEcran() < 4.0 || smartphone.getTailleEcran() > 7.0) {
                errors.rejectValue("tailleEcran", "tailleEcran.invalid", 
                    "La taille de l'écran doit être comprise entre 4.0 et 7.0 pouces");
            }
        }

        // Validation de la RAM
        if (smartphone.getTailleRam() != null) {
            if (smartphone.getTailleRam() < 2 || smartphone.getTailleRam() > 16) {
                errors.rejectValue("tailleRam", "tailleRam.invalid", 
                    "La taille de la RAM doit être comprise entre 2 et 16 Go");
            }
        }

        // Validation du stockage
        if (smartphone.getTailleStockage() != null) {
            if (smartphone.getTailleStockage() < 16 || smartphone.getTailleStockage() > 512) {
                errors.rejectValue("tailleStockage", "tailleStockage.invalid", 
                    "La taille du stockage doit être comprise entre 16 et 512 Go");
            }
        }

        // Validation de la batterie
        if (smartphone.getCapaciteBatterie() != null) {
            if (smartphone.getCapaciteBatterie() < 2000 || smartphone.getCapaciteBatterie() > 6000) {
                errors.rejectValue("capaciteBatterie", "capaciteBatterie.invalid", 
                    "La capacité de la batterie doit être comprise entre 2000 et 6000 mAh");
            }
        }
    }
} 