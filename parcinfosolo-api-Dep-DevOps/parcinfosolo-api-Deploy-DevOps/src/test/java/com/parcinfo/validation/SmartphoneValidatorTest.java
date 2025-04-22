package com.parcinfo.validation;

import com.parcinfo.model.Smartphone;
import com.parcinfo.model.TypeObjetNomade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour le validateur de smartphones.
 * Ces tests vérifient la validation des différents attributs d'un smartphone.
 */
class SmartphoneValidatorTest {

    private SmartphoneValidator validator;
    private Smartphone smartphone;
    private Errors errors;

    @BeforeEach
    void setUp() {
        validator = new SmartphoneValidator();
        smartphone = new Smartphone();
        smartphone.setNom("Test Smartphone");
        smartphone.setType(TypeObjetNomade.SMARTPHONE);
        smartphone.setNumeroSerie("TEST123");
        smartphone.setMarque("Test Brand");
        smartphone.setModele("Test Model");
        smartphone.setTailleEcran(6.1);
        smartphone.setTailleRam(8);
        smartphone.setTailleStockage(128);
        smartphone.setCapaciteBatterie(4000);
        smartphone.setA5g(true);
        smartphone.setADoubleSim(true);
        smartphone.setANfc(true);
        errors = new BeanPropertyBindingResult(smartphone, "smartphone");
    }

    @Test
    @DisplayName("Devrait valider un smartphone valide")
    void shouldValidateValidSmartphone() {
        validator.validate(smartphone, errors);
        assertFalse(errors.hasErrors(), "Un smartphone valide ne devrait pas avoir d'erreurs");
    }

    @Test
    @DisplayName("Devrait rejeter un smartphone avec une taille d'écran invalide")
    void shouldRejectInvalidScreenSize() {
        smartphone.setTailleEcran(3.5);
        validator.validate(smartphone, errors);
        assertTrue(errors.hasErrors(), "Un smartphone avec une taille d'écran invalide devrait avoir des erreurs");
        assertEquals("tailleEcran.invalid", errors.getFieldError("tailleEcran").getCode());
    }

    @Test
    @DisplayName("Devrait rejeter un smartphone avec une RAM invalide")
    void shouldRejectInvalidRam() {
        smartphone.setTailleRam(1);
        validator.validate(smartphone, errors);
        assertTrue(errors.hasErrors(), "Un smartphone avec une RAM invalide devrait avoir des erreurs");
        assertEquals("tailleRam.invalid", errors.getFieldError("tailleRam").getCode());
    }

    @Test
    @DisplayName("Devrait rejeter un smartphone avec un stockage invalide")
    void shouldRejectInvalidStorage() {
        smartphone.setTailleStockage(8);
        validator.validate(smartphone, errors);
        assertTrue(errors.hasErrors(), "Un smartphone avec un stockage invalide devrait avoir des erreurs");
        assertEquals("tailleStockage.invalid", errors.getFieldError("tailleStockage").getCode());
    }

    @Test
    @DisplayName("Devrait rejeter un smartphone avec une capacité de batterie invalide")
    void shouldRejectInvalidBatteryCapacity() {
        smartphone.setCapaciteBatterie(1500);
        validator.validate(smartphone, errors);
        assertTrue(errors.hasErrors(), "Un smartphone avec une capacité de batterie invalide devrait avoir des erreurs");
        assertEquals("capaciteBatterie.invalid", errors.getFieldError("capaciteBatterie").getCode());
    }

    @Test
    @DisplayName("Devrait valider un smartphone avec des valeurs nulles")
    void shouldValidateSmartphoneWithNullValues() {
        smartphone.setTailleEcran(null);
        smartphone.setTailleRam(null);
        smartphone.setTailleStockage(null);
        smartphone.setCapaciteBatterie(null);
        validator.validate(smartphone, errors);
        assertFalse(errors.hasErrors(), "Un smartphone avec des valeurs nulles devrait être valide");
    }

    @ParameterizedTest
    @ValueSource(doubles = {4.0, 5.0, 6.0, 7.0})
    @DisplayName("Devrait valider les tailles d'écran limites")
    void shouldValidateScreenSizeLimits(double screenSize) {
        smartphone.setTailleEcran(screenSize);
        validator.validate(smartphone, errors);
        assertFalse(errors.hasErrors(), "La taille d'écran " + screenSize + " devrait être valide");
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 8, 16})
    @DisplayName("Devrait valider les tailles de RAM limites")
    void shouldValidateRamLimits(int ramSize) {
        smartphone.setTailleRam(ramSize);
        validator.validate(smartphone, errors);
        assertFalse(errors.hasErrors(), "La taille de RAM " + ramSize + " devrait être valide");
    }

    @ParameterizedTest
    @ValueSource(ints = {16, 32, 128, 512})
    @DisplayName("Devrait valider les tailles de stockage limites")
    void shouldValidateStorageLimits(int storageSize) {
        smartphone.setTailleStockage(storageSize);
        validator.validate(smartphone, errors);
        assertFalse(errors.hasErrors(), "La taille de stockage " + storageSize + " devrait être valide");
    }

    @ParameterizedTest
    @ValueSource(ints = {2000, 3000, 4000, 6000})
    @DisplayName("Devrait valider les capacités de batterie limites")
    void shouldValidateBatteryCapacityLimits(int batteryCapacity) {
        smartphone.setCapaciteBatterie(batteryCapacity);
        validator.validate(smartphone, errors);
        assertFalse(errors.hasErrors(), "La capacité de batterie " + batteryCapacity + " devrait être valide");
    }

    @Test
    @DisplayName("Devrait valider le support de la méthode supports")
    void shouldSupportSmartphoneClass() {
        assertTrue(validator.supports(Smartphone.class), "Le validateur devrait supporter la classe Smartphone");
        assertFalse(validator.supports(String.class), "Le validateur ne devrait pas supporter la classe String");
    }
} 