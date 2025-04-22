package com.parcinfo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AffectationTest {

    private Affectation affectation;
    private Personne personne;

    @BeforeEach
    void setUp() {
        affectation = new Affectation();
        personne = new Personne();
    }

    @Test
    @DisplayName("Devrait créer une affectation avec des valeurs valides")
    void shouldCreateValidAffectation() {
        // Arrange
        LocalDate dateAffectation = LocalDate.now();

        // Act
        affectation.setDateAffectation(dateAffectation);
        affectation.setPersonne(personne);

        // Assert
        assertEquals(dateAffectation, affectation.getDateAffectation());
        assertEquals(personne, affectation.getPersonne());
    }

    @Test
    @DisplayName("Devrait comparer correctement deux affectations")
    void shouldCompareAffectations() {
        // Arrange
        Affectation affectation1 = new Affectation();
        Affectation affectation2 = new Affectation();
        
        affectation1.setDateAffectation(LocalDate.now());
        affectation2.setDateAffectation(affectation1.getDateAffectation());
        
        // Act & Assert
        assertEquals(affectation1, affectation2);
        assertEquals(affectation1.hashCode(), affectation2.hashCode());
        
        // Modifier un champ
        affectation2.setDateAffectation(LocalDate.now().plusDays(1));
        assertNotEquals(affectation1, affectation2);
    }

    @Test
    @DisplayName("Devrait gérer les valeurs nulles")
    void shouldHandleNullValues() {
        // Act & Assert
        assertNull(affectation.getDateAffectation());
        assertNull(affectation.getPersonne());
    }

    @Test
    @DisplayName("Devrait mettre à jour les informations de l'affectation")
    void shouldUpdateAffectationInfo() {
        // Arrange
        affectation.setDateAffectation(LocalDate.now());

        // Act
        LocalDate newDate = LocalDate.now().plusDays(1);
        affectation.setDateAffectation(newDate);

        // Assert
        assertEquals(newDate, affectation.getDateAffectation());
    }
} 