package com.parcinfo.validation;

import com.parcinfo.model.OrdinateurPortable;
import com.parcinfo.model.ObjetNomade.EtatObjetNomade;
import com.parcinfo.model.TypeObjetNomade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour le validateur d'ordinateurs portables.
 */
class OrdinateurPortableValidatorTest {

    private OrdinateurPortableValidator validator;
    private OrdinateurPortable ordinateur;

    @BeforeEach
    void setUp() {
        validator = new OrdinateurPortableValidator();
        ordinateur = new OrdinateurPortable();
        
        // Initialisation avec des valeurs valides
        ordinateur.setNom("Test Ordinateur");
        ordinateur.setNumeroSerie("123456789");
        ordinateur.setMarque("Marque Test");
        ordinateur.setModele("Modèle Test");
        ordinateur.setType(TypeObjetNomade.LAPTOP);
        ordinateur.setEtat(EtatObjetNomade.NEUF);
        ordinateur.setDateAcquisition(LocalDateTime.now().minusDays(1));
        ordinateur.setSystemeExploitation("Windows 11");
        ordinateur.setTailleEcran(15.6);
        ordinateur.setProcesseur("Intel Core i7");
        ordinateur.setTailleRam(16);
        ordinateur.setTailleStockage(512);
        ordinateur.setTypeStockage("SSD");
        ordinateur.setCarteGraphique("NVIDIA RTX 3060");
        ordinateur.setAutonomieBatterie(8);
    }

    @Test
    @DisplayName("Devrait valider un ordinateur portable avec des données valides")
    void shouldValidateValidOrdinateurPortable() {
        List<String> errors = validator.validate(ordinateur);
        assertTrue(errors.isEmpty(), "Ne devrait pas y avoir d'erreurs pour un ordinateur portable valide");
    }

    @Test
    @DisplayName("Devrait détecter un écran trop petit")
    void shouldDetectSmallScreen() {
        ordinateur.setTailleEcran(10.0);
        List<String> errors = validator.validate(ordinateur);
        assertTrue(!errors.isEmpty(), "Devrait détecter un écran trop petit");
        assertTrue(errors.stream().anyMatch(e -> e.contains("écran") && e.contains("minimum")));
    }

    @Test
    @DisplayName("Devrait détecter une RAM insuffisante")
    void shouldDetectInsufficientRam() {
        ordinateur.setTailleRam(2);
        List<String> errors = validator.validate(ordinateur);
        assertTrue(!errors.isEmpty(), "Devrait détecter une RAM insuffisante");
        assertTrue(errors.stream().anyMatch(e -> e.contains("RAM") && e.contains("minimum")));
    }

    @Test
    @DisplayName("Devrait détecter un stockage insuffisant")
    void shouldDetectInsufficientStorage() {
        ordinateur.setTailleStockage(64);
        List<String> errors = validator.validate(ordinateur);
        assertTrue(!errors.isEmpty(), "Devrait détecter un stockage insuffisant");
        assertTrue(errors.stream().anyMatch(e -> e.contains("stockage") && e.contains("minimum")));
    }

    @Test
    @DisplayName("Devrait détecter une autonomie batterie insuffisante")
    void shouldDetectInsufficientBatteryLife() {
        ordinateur.setAutonomieBatterie(2);
        List<String> errors = validator.validate(ordinateur);
        assertTrue(!errors.isEmpty(), "Devrait détecter une autonomie batterie insuffisante");
        assertTrue(errors.stream().anyMatch(e -> e.contains("batterie") && e.contains("minimum")));
    }

    @Test
    @DisplayName("Devrait détecter un type de stockage invalide")
    void shouldDetectInvalidStorageType() {
        ordinateur.setTypeStockage("HDD");
        List<String> errors = validator.validate(ordinateur);
        assertTrue(!errors.isEmpty(), "Devrait détecter un type de stockage invalide");
        assertTrue(errors.stream().anyMatch(e -> e.contains("stockage") && e.contains("SSD")));
    }

    @Test
    @DisplayName("Devrait détecter une carte graphique manquante")
    void shouldDetectMissingGraphicsCard() {
        ordinateur.setCarteGraphique(null);
        List<String> errors = validator.validate(ordinateur);
        assertTrue(!errors.isEmpty(), "Devrait détecter une carte graphique manquante");
        assertTrue(errors.stream().anyMatch(e -> e.contains("carte graphique")));
    }

    @Test
    @DisplayName("Devrait détecter un système d'exploitation manquant")
    void shouldDetectMissingOperatingSystem() {
        ordinateur.setSystemeExploitation(null);
        List<String> errors = validator.validate(ordinateur);
        assertTrue(!errors.isEmpty(), "Devrait détecter un système d'exploitation manquant");
        assertTrue(errors.stream().anyMatch(e -> e.contains("système d'exploitation")));
    }

    @Test
    @DisplayName("Devrait détecter un processeur manquant")
    void shouldDetectMissingProcessor() {
        ordinateur.setProcesseur(null);
        List<String> errors = validator.validate(ordinateur);
        assertTrue(!errors.isEmpty(), "Devrait détecter un processeur manquant");
        assertTrue(errors.stream().anyMatch(e -> e.contains("processeur")));
    }
} 