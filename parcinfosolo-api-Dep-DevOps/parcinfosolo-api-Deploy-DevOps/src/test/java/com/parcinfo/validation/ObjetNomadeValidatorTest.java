package com.parcinfo.validation;

import com.parcinfo.model.ObjetNomade;
import com.parcinfo.model.ObjetNomade.EtatObjetNomade;
import com.parcinfo.model.TypeObjetNomade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour le validateur d'objets nomades.
 */
class ObjetNomadeValidatorTest {

    private ObjetNomadeValidator validator;
    private ObjetNomade objetNomade;

    @BeforeEach
    void setUp() {
        validator = new ObjetNomadeValidator();
        objetNomade = new ObjetNomade();
        
        // Initialisation avec des valeurs valides
        objetNomade.setNom("Test Objet");
        objetNomade.setType(TypeObjetNomade.SMARTPHONE);
        objetNomade.setNumeroSerie("123456789");
        objetNomade.setMarque("Marque Test");
        objetNomade.setModele("Modèle Test");
        objetNomade.setEtat(EtatObjetNomade.NEUF);
        objetNomade.setDateAcquisition(LocalDateTime.now().minusDays(1));
        objetNomade.setSystemeExploitation("Android");
        objetNomade.setTailleEcran(6.0);
        objetNomade.setCapaciteBatterie(3000);
        objetNomade.setResolutionCamera("1920x1080");
        objetNomade.setTailleStockage(64);
        objetNomade.setTailleRam(8);
        objetNomade.setProcesseur("Snapdragon 888");
    }

    @Test
    @DisplayName("Devrait valider un objet nomade avec des données valides")
    void shouldValidateValidObjetNomade() {
        List<String> errors = validator.validate(objetNomade);
        assertTrue(errors.isEmpty(), "Ne devrait pas y avoir d'erreurs pour un objet valide");
    }

    @Test
    @DisplayName("Devrait détecter une date d'acquisition dans le futur")
    void shouldDetectFutureAcquisitionDate() {
        objetNomade.setDateAcquisition(LocalDateTime.now().plusDays(1));
        List<String> errors = validator.validate(objetNomade);
        assertFalse(errors.isEmpty(), "Devrait détecter une date d'acquisition dans le futur");
        assertTrue(errors.stream().anyMatch(e -> e.contains("date d'acquisition") && e.contains("futur")));
    }

    @Test
    @DisplayName("Devrait détecter une date de mise en service antérieure à la date d'acquisition")
    void shouldDetectServiceDateBeforeAcquisitionDate() {
        objetNomade.setDateMiseEnService(objetNomade.getDateAcquisition().minusDays(1));
        List<String> errors = validator.validate(objetNomade);
        assertFalse(errors.isEmpty(), "Devrait détecter une date de mise en service antérieure à la date d'acquisition");
        assertTrue(errors.stream().anyMatch(e -> e.contains("mise en service") && e.contains("antérieure")));
    }

    @Test
    @DisplayName("Devrait détecter une date de maintenance antérieure à la date d'acquisition")
    void shouldDetectMaintenanceDateBeforeAcquisitionDate() {
        objetNomade.setDateDerniereMaintenance(objetNomade.getDateAcquisition().minusDays(1));
        List<String> errors = validator.validate(objetNomade);
        assertFalse(errors.isEmpty(), "Devrait détecter une date de maintenance antérieure à la date d'acquisition");
        assertTrue(errors.stream().anyMatch(e -> e.contains("maintenance") && e.contains("antérieure")));
    }

    @Test
    @DisplayName("Devrait détecter une RAM supérieure au stockage")
    void shouldDetectRamGreaterThanStorage() {
        objetNomade.setTailleRam(128);
        objetNomade.setTailleStockage(64);
        List<String> errors = validator.validate(objetNomade);
        assertFalse(errors.isEmpty(), "Devrait détecter une RAM supérieure au stockage");
        assertTrue(errors.stream().anyMatch(e -> e.contains("RAM") && e.contains("supérieure")));
    }

    @Test
    @DisplayName("Devrait détecter un format de résolution de caméra invalide")
    void shouldDetectInvalidCameraResolutionFormat() {
        objetNomade.setResolutionCamera("1080p");
        List<String> errors = validator.validate(objetNomade);
        assertFalse(errors.isEmpty(), "Devrait détecter un format de résolution de caméra invalide");
        assertTrue(errors.stream().anyMatch(e -> e.contains("résolution") && e.contains("format")));
    }

    @Test
    @DisplayName("Devrait détecter un système d'exploitation non supporté")
    void shouldDetectUnsupportedOperatingSystem() {
        objetNomade.setSystemeExploitation("Linux");
        List<String> errors = validator.validate(objetNomade);
        assertFalse(errors.isEmpty(), "Devrait détecter un système d'exploitation non supporté");
        assertTrue(errors.stream().anyMatch(e -> e.contains("système d'exploitation")));
    }
} 