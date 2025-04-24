package com.parcinfo.validation;

import com.parcinfo.model.OrdinateurPortable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrdinateurPortableValidator {
    
    public List<String> validate(OrdinateurPortable ordinateur) {
        List<String> errors = new ArrayList<>();
        
        if (ordinateur == null) {
            errors.add("L'ordinateur portable ne peut pas être null");
            return errors;
        }

        if (ordinateur.getSystemeExploitation() == null || ordinateur.getSystemeExploitation().trim().isEmpty()) {
            errors.add("Le système d'exploitation est obligatoire");
        }

        if (ordinateur.getTailleEcran() != null && ordinateur.getTailleEcran() < 11.0) {
            errors.add("La taille de l'écran doit être d'au moins 11 pouces (minimum)");
        }

        if (ordinateur.getProcesseur() == null || ordinateur.getProcesseur().trim().isEmpty()) {
            errors.add("Le processeur est obligatoire");
        }

        if (ordinateur.getTailleRam() != null && ordinateur.getTailleRam() < 8) {
            errors.add("La taille de la RAM doit être d'au moins 8 Go (minimum)");
        }

        if (ordinateur.getTailleStockage() != null && ordinateur.getTailleStockage() < 256) {
            errors.add("La taille du stockage doit être d'au moins 256 Go (minimum)");
        }

        if (ordinateur.getAutonomieBatterie() != null && ordinateur.getAutonomieBatterie() < 6) {
            errors.add("L'autonomie de la batterie doit être d'au moins 6 heures (minimum)");
        }

        if (ordinateur.getTypeStockage() != null && !ordinateur.getTypeStockage().equalsIgnoreCase("SSD")) {
            errors.add("Le type de stockage doit être SSD");
        }

        if (ordinateur.getCarteGraphique() == null || ordinateur.getCarteGraphique().trim().isEmpty()) {
            errors.add("La carte graphique est obligatoire");
        }
        
        return errors;
    }
} 