package com.parcinfo.validation;

import com.parcinfo.model.Tablette;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour le validateur de tablettes.
 */
class TabletteValidatorTest {

    private TabletteValidator validator;
    private Tablette tablette;

    @BeforeEach
    void setUp() {
        validator = new TabletteValidator();
        tablette = new Tablette();
        tablette.setNom("Test Tablette");
        tablette.setTailleEcran(10.1);
        tablette.setTailleRam(4);
        tablette.setTailleStockage(64);
        tablette.setCapaciteBatterie(6000);
        tablette.setSupportClavier(true);
        tablette.setSupportStylet(true);
    }

    @Test
    @DisplayName("Devrait valider une tablette avec des spécifications correctes")
    void shouldValidateTabletteWithCorrectSpecifications() {
        List<String> errors = validator.validate(tablette);
        assertTrue(errors.isEmpty(), "Ne devrait pas avoir d'erreurs pour des spécifications valides");
    }

    @Test
    @DisplayName("Devrait rejeter une tablette avec une taille d'écran invalide")
    void shouldRejectTabletteWithInvalidScreenSize() {
        tablette.setTailleEcran(7.0);
        List<String> errors = validator.validate(tablette);
        assertFalse(errors.isEmpty(), "Devrait avoir des erreurs pour une taille d'écran invalide");
        assertTrue(errors.stream().anyMatch(error -> error.contains("8 pouces")), 
            "Devrait avoir une erreur sur la taille de l'écran");
    }

    @Test
    @DisplayName("Devrait rejeter une tablette avec une RAM invalide")
    void shouldRejectTabletteWithInvalidRam() {
        tablette.setTailleRam(1);
        List<String> errors = validator.validate(tablette);
        assertFalse(errors.isEmpty(), "Devrait avoir des erreurs pour une RAM invalide");
        assertTrue(errors.stream().anyMatch(error -> error.contains("2 Go de RAM")), 
            "Devrait avoir une erreur sur la RAM");
    }

    @Test
    @DisplayName("Devrait rejeter une tablette avec un stockage invalide")
    void shouldRejectTabletteWithInvalidStorage() {
        tablette.setTailleStockage(8);
        List<String> errors = validator.validate(tablette);
        assertFalse(errors.isEmpty(), "Devrait avoir des erreurs pour un stockage invalide");
        assertTrue(errors.stream().anyMatch(error -> error.contains("16 Go de stockage")), 
            "Devrait avoir une erreur sur le stockage");
    }

    @Test
    @DisplayName("Devrait rejeter une tablette avec une capacité de batterie invalide")
    void shouldRejectTabletteWithInvalidBatteryCapacity() {
        tablette.setCapaciteBatterie(4000);
        List<String> errors = validator.validate(tablette);
        assertFalse(errors.isEmpty(), "Devrait avoir des erreurs pour une capacité de batterie invalide");
        assertTrue(errors.stream().anyMatch(error -> error.contains("5000 mAh")), 
            "Devrait avoir une erreur sur la batterie");
    }

    @Test
    @DisplayName("Devrait valider une tablette avec des valeurs nulles")
    void shouldValidateTabletteWithNullValues() {
        tablette.setTailleEcran(null);
        tablette.setTailleRam(null);
        tablette.setTailleStockage(null);
        tablette.setCapaciteBatterie(null);
        tablette.setSupportClavier(null);
        tablette.setSupportStylet(null);
        List<String> errors = validator.validate(tablette);
        assertTrue(errors.isEmpty(), "Ne devrait pas avoir d'erreurs pour des valeurs nulles");
    }

    @Test
    @DisplayName("Devrait rejeter une tablette avec support clavier et écran trop petit")
    void shouldRejectTabletteWithKeyboardSupportAndSmallScreen() {
        tablette.setSupportClavier(true);
        tablette.setTailleEcran(9.5);
        List<String> errors = validator.validate(tablette);
        assertFalse(errors.isEmpty(), "Devrait avoir des erreurs pour un écran trop petit avec support clavier");
        assertTrue(errors.stream().anyMatch(error -> error.contains("10 pouces")), 
            "Devrait avoir une erreur sur la taille d'écran minimale pour le support clavier");
    }

    @Test
    @DisplayName("Devrait rejeter une tablette avec support stylet et écran trop petit")
    void shouldRejectTabletteWithStylusSupportAndSmallScreen() {
        tablette.setSupportStylet(true);
        tablette.setTailleEcran(8.5);
        List<String> errors = validator.validate(tablette);
        assertFalse(errors.isEmpty(), "Devrait avoir des erreurs pour un écran trop petit avec support stylet");
        assertTrue(errors.stream().anyMatch(error -> error.contains("9 pouces")), 
            "Devrait avoir une erreur sur la taille d'écran minimale pour le support stylet");
    }
} 