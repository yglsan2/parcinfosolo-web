package com.parcinfo.validation;

import com.parcinfo.model.ObjetNomade;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Validateur de base pour les objets nomades.
 * Les validations de base sont gérées par les annotations de validation.
 * Ce validateur ajoute des validations spécifiques qui ne peuvent pas être
 * exprimées par les annotations.
 */
@Component
public class ObjetNomadeValidator {

    /**
     * Valide un objet nomade et retourne une liste d'erreurs éventuelles.
     * 
     * @param objetNomade L'objet nomade à valider
     * @return Liste des messages d'erreur, vide si l'objet est valide
     */
    public List<String> validate(ObjetNomade objetNomade) {
        List<String> errors = new ArrayList<>();

        // Validation des champs communs
        if (objetNomade.getMarque() == null || objetNomade.getMarque().trim().isEmpty()) {
            errors.add("La marque est obligatoire");
        }

        if (objetNomade.getModele() == null || objetNomade.getModele().trim().isEmpty()) {
            errors.add("Le modèle est obligatoire");
        }

        if (objetNomade.getNumeroSerie() == null || objetNomade.getNumeroSerie().trim().isEmpty()) {
            errors.add("Le numéro de série est obligatoire");
        }

        if (objetNomade.getDateAcquisition() == null) {
            errors.add("La date d'acquisition est obligatoire");
        }

        if (objetNomade.getEtat() == null) {
            errors.add("L'état du matériel est obligatoire");
        }

        // Vérification de la taille de l'écran
        if (objetNomade.getTailleEcran() == null || objetNomade.getTailleEcran() <= 0) {
            errors.add("La taille de l'écran doit être supérieure à 0");
        }

        // Vérification de la capacité de la batterie
        if (objetNomade.getCapaciteBatterie() == null || objetNomade.getCapaciteBatterie() <= 0) {
            errors.add("La capacité de la batterie doit être supérieure à 0");
        }

        // Vérification de la résolution de la caméra
        if (objetNomade.getResolutionCamera() != null && !objetNomade.getResolutionCamera().trim().isEmpty()) {
            String resolution = objetNomade.getResolutionCamera().trim();
            if (!resolution.matches("^\\d+[xX]\\d+$")) {
                errors.add("La résolution de la caméra doit être au format 'largeurxhauteur' (ex: 1920x1080)");
            }
        }

        // Vérification du système d'exploitation
        if (objetNomade.getSystemeExploitation() != null && !objetNomade.getSystemeExploitation().trim().isEmpty()) {
            String os = objetNomade.getSystemeExploitation().trim().toLowerCase();
            if (!os.equals("android") && !os.equals("ios") && !os.equals("windows") && !os.equals("harmonyos")) {
                errors.add("Le système d'exploitation doit être Android, iOS, Windows ou HarmonyOS");
            }
        }

        // Vérification de la taille du stockage
        if (objetNomade.getTailleStockage() == null || objetNomade.getTailleStockage() <= 0) {
            errors.add("La taille du stockage doit être supérieure à 0");
        }

        // Vérification de la taille de la RAM
        if (objetNomade.getTailleRam() == null || objetNomade.getTailleRam() <= 0) {
            errors.add("La taille de la RAM doit être supérieure à 0");
        }

        // Vérification du processeur
        if (objetNomade.getProcesseur() == null || objetNomade.getProcesseur().trim().isEmpty()) {
            errors.add("Le processeur est obligatoire");
        }

        // Vérification de la cohérence des dates
        if (objetNomade.getDateMiseEnService() != null && objetNomade.getDateAcquisition() != null
            && objetNomade.getDateMiseEnService().isBefore(objetNomade.getDateAcquisition())) {
            errors.add("La date de mise en service ne peut pas être antérieure à la date d'acquisition");
        }

        if (objetNomade.getDateDerniereMaintenance() != null && objetNomade.getDateAcquisition() != null
            && objetNomade.getDateDerniereMaintenance().isBefore(objetNomade.getDateAcquisition())) {
            errors.add("La date de dernière maintenance ne peut pas être antérieure à la date d'acquisition");
        }

        // Vérification que la date d'acquisition n'est pas dans le futur
        if (objetNomade.getDateAcquisition() != null && objetNomade.getDateAcquisition().isAfter(LocalDateTime.now())) {
            errors.add("La date d'acquisition ne peut pas être dans le futur");
        }

        // Vérification de la cohérence des caractéristiques techniques
        if (objetNomade.getTailleRam() != null && objetNomade.getTailleStockage() != null
            && objetNomade.getTailleRam() > objetNomade.getTailleStockage()) {
            errors.add("La taille de la RAM ne peut pas être supérieure à la taille du stockage");
        }

        return errors;
    }
} 