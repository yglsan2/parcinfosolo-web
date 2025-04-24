package com.parcinfo.validation;

import com.parcinfo.model.Personne;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Validateur pour les objets Personne.
 * Vérifie que les données d'une personne sont valides avant leur enregistrement.
 */
@Component
public class PersonneValidator {

    /**
     * Valide une personne et retourne une liste d'erreurs éventuelles.
     * Les validations de base sont gérées par les annotations de validation.
     * Ce validateur ajoute des validations spécifiques qui ne peuvent pas être
     * exprimées par les annotations.
     * 
     * @param personne La personne à valider
     * @return Liste des messages d'erreur, vide si la personne est valide
     */
    public List<String> validate(Personne personne) {
        List<String> errors = new ArrayList<>();

        // Vérification du nom
        if (personne.getNom() == null || personne.getNom().trim().isEmpty()) {
            errors.add("Le nom est obligatoire");
        } else if (personne.getNom().length() > 30) {
            errors.add("Le nom ne doit pas dépasser 30 caractères");
        }

        // Vérification du prénom
        if (personne.getPrenom() == null || personne.getPrenom().trim().isEmpty()) {
            errors.add("Le prénom est obligatoire");
        } else if (personne.getPrenom().length() > 25) {
            errors.add("Le prénom ne doit pas dépasser 25 caractères");
        }

        // Vérification de l'adresse
        if (personne.getAdresse() == null || personne.getAdresse().trim().isEmpty()) {
            errors.add("L'adresse est obligatoire");
        } else if (personne.getAdresse().length() > 50) {
            errors.add("L'adresse ne doit pas dépasser 50 caractères");
        }

        // Vérification de l'email
        if (personne.getEmail() == null || personne.getEmail().trim().isEmpty()) {
            errors.add("L'email est obligatoire");
        } else if (!personne.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.add("L'email n'est pas valide");
        }

        // Vérification du téléphone
        if (personne.getTelephone() == null || personne.getTelephone().trim().isEmpty()) {
            errors.add("Le numéro de téléphone est obligatoire");
        } else if (!personne.getTelephone().matches("^\\+?[0-9. -]{10,}$")) {
            errors.add("Le numéro de téléphone n'est pas valide");
        }

        // Vérification de la date de naissance
        if (personne.getDateNaissance() == null) {
            errors.add("La date de naissance est obligatoire");
        } else if (personne.getDateNaissance().isAfter(LocalDate.now())) {
            errors.add("La date de naissance ne peut pas être dans le futur");
        }

        // Vérification du format du téléphone (validation spécifique)
        if (personne.getTelephone() != null && !personne.getTelephone().trim().isEmpty() 
            && !personne.getTelephone().matches("^\\+?[0-9. -]{10,}$")) {
            errors.add("Le numéro de téléphone n'est pas valide");
        }

        return errors;
    }
} 