package com.parcinfo.validation;

import com.parcinfo.model.Peripherique;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Validateur pour les objets Peripherique.
 * Vérifie que les données d'un périphérique sont valides avant leur enregistrement.
 */
@Component
public class PeripheriqueValidator {

    /**
     * Valide un périphérique et retourne une liste d'erreurs éventuelles.
     * 
     * @param peripherique Le périphérique à valider
     * @return Liste des messages d'erreur, vide si le périphérique est valide
     */
    public List<String> validate(Peripherique peripherique) {
        List<String> errors = new ArrayList<>();

        if (peripherique.getMarque() == null || peripherique.getMarque().trim().isEmpty()) {
            errors.add("La marque est obligatoire");
        }

        if (peripherique.getModele() == null || peripherique.getModele().trim().isEmpty()) {
            errors.add("Le modèle est obligatoire");
        }

        if (peripherique.getNumeroSerie() == null || peripherique.getNumeroSerie().trim().isEmpty()) {
            errors.add("Le numéro de série est obligatoire");
        }

        if (peripherique.getType() == null) {
            errors.add("Le type de périphérique est obligatoire");
        }

        return errors;
    }
} 