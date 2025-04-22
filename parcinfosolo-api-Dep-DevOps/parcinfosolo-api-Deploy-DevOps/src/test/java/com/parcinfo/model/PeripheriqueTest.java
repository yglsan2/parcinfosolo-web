package com.parcinfo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PeripheriqueTest {

    private Peripherique peripherique;

    @BeforeEach
    void setUp() {
        peripherique = new Peripherique();
    }

    @Test
    @DisplayName("Devrait créer un périphérique avec des valeurs valides")
    void shouldCreateValidPeripherique() {
        // Arrange
        String marque = "Logitech";
        String modele = "MX Master 3";
        String numeroSerie = "LGT123456";
        TypePeripherique type = TypePeripherique.SOURIS;
        Peripherique.EtatPeripherique etat = Peripherique.EtatPeripherique.NEUF;
        LocalDateTime dateAcquisition = LocalDateTime.now();
        String commentaire = "Souris ergonomique";

        // Act
        peripherique.setMarque(marque);
        peripherique.setModele(modele);
        peripherique.setNumeroSerie(numeroSerie);
        peripherique.setType(type);
        peripherique.setEtat(etat);
        peripherique.setDateAcquisition(dateAcquisition);
        peripherique.setCommentaire(commentaire);

        // Assert
        assertEquals(marque, peripherique.getMarque());
        assertEquals(modele, peripherique.getModele());
        assertEquals(numeroSerie, peripherique.getNumeroSerie());
        assertEquals(type, peripherique.getType());
        assertEquals(etat, peripherique.getEtat());
        assertEquals(dateAcquisition, peripherique.getDateAcquisition());
        assertEquals(commentaire, peripherique.getCommentaire());
        assertTrue(peripherique.isEstActif());
    }

    @Test
    @DisplayName("Devrait gérer les associations avec Ordinateur et ObjetNomade")
    void shouldHandleAssociations() {
        // Arrange
        Ordinateur ordinateur = new Ordinateur();
        ObjetNomade objetNomade = new ObjetNomade();

        // Act & Assert
        // Test association avec Ordinateur
        peripherique.setOrdinateur(ordinateur);
        assertNotNull(peripherique.getOrdinateur());
        assertNull(peripherique.getObjetNomade());

        // Test association avec ObjetNomade
        peripherique.setOrdinateur(null);
        peripherique.setObjetNomade(objetNomade);
        assertNull(peripherique.getOrdinateur());
        assertNotNull(peripherique.getObjetNomade());
    }

    @Test
    @DisplayName("Devrait valider l'état du périphérique")
    void shouldValidateEtatPeripherique() {
        // Act & Assert
        peripherique.setEtat(Peripherique.EtatPeripherique.NEUF);
        assertEquals(Peripherique.EtatPeripherique.NEUF, peripherique.getEtat());

        peripherique.setEtat(Peripherique.EtatPeripherique.BON);
        assertEquals(Peripherique.EtatPeripherique.BON, peripherique.getEtat());

        peripherique.setEtat(Peripherique.EtatPeripherique.MOYEN);
        assertEquals(Peripherique.EtatPeripherique.MOYEN, peripherique.getEtat());

        peripherique.setEtat(Peripherique.EtatPeripherique.MAUVAIS);
        assertEquals(Peripherique.EtatPeripherique.MAUVAIS, peripherique.getEtat());

        peripherique.setEtat(Peripherique.EtatPeripherique.HORS_SERVICE);
        assertEquals(Peripherique.EtatPeripherique.HORS_SERVICE, peripherique.getEtat());
    }

    @Test
    @DisplayName("Devrait gérer la désactivation du périphérique")
    void shouldHandleDeactivation() {
        // Assert initial state
        assertTrue(peripherique.isEstActif());

        // Act
        peripherique.setEstActif(false);

        // Assert
        assertFalse(peripherique.isEstActif());
    }

    @Test
    @DisplayName("Devrait gérer les dates correctement")
    void shouldHandleDates() {
        // Arrange
        LocalDateTime dateAcquisition = LocalDateTime.now();
        LocalDateTime dateMiseEnService = LocalDateTime.now().plusDays(1);

        // Act
        peripherique.setDateAcquisition(dateAcquisition);
        peripherique.setDateMiseEnService(dateMiseEnService);

        // Assert
        assertEquals(dateAcquisition, peripherique.getDateAcquisition());
        assertEquals(dateMiseEnService, peripherique.getDateMiseEnService());
    }
} 