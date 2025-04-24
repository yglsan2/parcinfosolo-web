package com.parcinfo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe modèle OrdinateurPortable.
 */
class OrdinateurPortableTest {

    private OrdinateurPortable ordinateur;

    @BeforeEach
    void setUp() {
        ordinateur = new OrdinateurPortable();
    }

    @Test
    @DisplayName("Devrait créer un ordinateur portable avec des valeurs valides")
    void shouldCreateValidOrdinateurPortable() {
        // Arrange
        String nom = "Test Ordinateur";
        String numeroSerie = "123456789";
        String marque = "Marque Test";
        String modele = "Modèle Test";
        ObjetNomade.EtatObjetNomade etat = ObjetNomade.EtatObjetNomade.NEUF;
        LocalDateTime dateAcquisition = LocalDateTime.now();
        String systemeExploitation = "Windows 11";
        Double tailleEcran = 15.6;
        String processeur = "Intel Core i7";
        Integer tailleRam = 16;
        Integer tailleStockage = 512;
        String typeStockage = "SSD";
        String carteGraphique = "NVIDIA RTX 3060";
        Integer autonomieBatterie = 8;

        // Act
        ordinateur.setNom(nom);
        ordinateur.setNumeroSerie(numeroSerie);
        ordinateur.setMarque(marque);
        ordinateur.setModele(modele);
        ordinateur.setType(TypeObjetNomade.LAPTOP);
        ordinateur.setEtat(etat);
        ordinateur.setDateAcquisition(dateAcquisition);
        ordinateur.setSystemeExploitation(systemeExploitation);
        ordinateur.setTailleEcran(tailleEcran);
        ordinateur.setProcesseur(processeur);
        ordinateur.setTailleRam(tailleRam);
        ordinateur.setTailleStockage(tailleStockage);
        ordinateur.setTypeStockage(typeStockage);
        ordinateur.setCarteGraphique(carteGraphique);
        ordinateur.setAutonomieBatterie(autonomieBatterie);

        // Assert
        assertEquals(nom, ordinateur.getNom());
        assertEquals(numeroSerie, ordinateur.getNumeroSerie());
        assertEquals(marque, ordinateur.getMarque());
        assertEquals(modele, ordinateur.getModele());
        assertEquals(TypeObjetNomade.LAPTOP, ordinateur.getType());
        assertEquals(etat, ordinateur.getEtat());
        assertEquals(dateAcquisition, ordinateur.getDateAcquisition());
        assertEquals(systemeExploitation, ordinateur.getSystemeExploitation());
        assertEquals(tailleEcran, ordinateur.getTailleEcran());
        assertEquals(processeur, ordinateur.getProcesseur());
        assertEquals(tailleRam, ordinateur.getTailleRam());
        assertEquals(tailleStockage, ordinateur.getTailleStockage());
        assertEquals(typeStockage, ordinateur.getTypeStockage());
        assertEquals(carteGraphique, ordinateur.getCarteGraphique());
        assertEquals(autonomieBatterie, ordinateur.getAutonomieBatterie());
    }

    @Test
    @DisplayName("Devrait mettre à jour les propriétés d'un ordinateur portable")
    void shouldUpdateOrdinateurPortableProperties() {
        // Arrange
        ordinateur.setNom("Ordinateur Initial");
        ordinateur.setTailleEcran(14.0);
        ordinateur.setTailleRam(8);
        ordinateur.setAutonomieBatterie(6);

        // Act
        ordinateur.setNom("Ordinateur Mis à jour");
        ordinateur.setTailleEcran(16.0);
        ordinateur.setTailleRam(32);
        ordinateur.setAutonomieBatterie(10);

        // Assert
        assertEquals("Ordinateur Mis à jour", ordinateur.getNom());
        assertEquals(16.0, ordinateur.getTailleEcran());
        assertEquals(32, ordinateur.getTailleRam());
        assertEquals(10, ordinateur.getAutonomieBatterie());
    }

    @Test
    @DisplayName("Devrait comparer correctement deux ordinateurs portables")
    void shouldCompareOrdinateurPortables() {
        // Arrange
        OrdinateurPortable ordinateur1 = new OrdinateurPortable();
        OrdinateurPortable ordinateur2 = new OrdinateurPortable();
        
        ordinateur1.setNom("Ordinateur 1");
        ordinateur1.setNumeroSerie("123456789");
        ordinateur1.setTailleEcran(15.6);
        
        ordinateur2.setNom("Ordinateur 1");
        ordinateur2.setNumeroSerie("123456789");
        ordinateur2.setTailleEcran(15.6);
        
        // Act & Assert
        assertEquals(ordinateur1, ordinateur2, "Les deux ordinateurs devraient être égaux");
        
        // Modifier un ordinateur
        ordinateur2.setTailleEcran(16.0);
        
        // Act & Assert
        assertNotEquals(ordinateur1, ordinateur2, "Les deux ordinateurs ne devraient plus être égaux");
    }

    @Test
    @DisplayName("Devrait calculer correctement le hash code")
    void shouldCalculateHashCode() {
        // Arrange
        OrdinateurPortable ordinateur1 = new OrdinateurPortable();
        OrdinateurPortable ordinateur2 = new OrdinateurPortable();
        
        ordinateur1.setNom("Ordinateur 1");
        ordinateur1.setNumeroSerie("123456789");
        ordinateur1.setTailleEcran(15.6);
        
        ordinateur2.setNom("Ordinateur 1");
        ordinateur2.setNumeroSerie("123456789");
        ordinateur2.setTailleEcran(15.6);
        
        // Act & Assert
        assertEquals(ordinateur1.hashCode(), ordinateur2.hashCode(), 
                "Les hash codes devraient être égaux pour des objets égaux");
        
        // Modifier un ordinateur
        ordinateur2.setTailleEcran(16.0);
        
        // Act & Assert
        assertNotEquals(ordinateur1.hashCode(), ordinateur2.hashCode(), 
                "Les hash codes ne devraient plus être égaux pour des objets différents");
    }

    @Test
    @DisplayName("Devrait gérer correctement les valeurs nulles")
    void shouldHandleNullValues() {
        // Act & Assert
        assertNull(ordinateur.getSystemeExploitation());
        assertNull(ordinateur.getProcesseur());
        assertNull(ordinateur.getCarteGraphique());
        assertNull(ordinateur.getTypeStockage());
    }

    @Test
    @DisplayName("Devrait valider les valeurs minimales")
    void shouldValidateMinimumValues() {
        // Act
        ordinateur.setTailleEcran(0.0);
        ordinateur.setTailleRam(0);
        ordinateur.setTailleStockage(0);
        ordinateur.setAutonomieBatterie(0);

        // Assert
        assertEquals(0.0, ordinateur.getTailleEcran());
        assertEquals(0, ordinateur.getTailleRam());
        assertEquals(0, ordinateur.getTailleStockage());
        assertEquals(0, ordinateur.getAutonomieBatterie());
    }

    @Test
    @DisplayName("Devrait copier correctement les propriétés")
    void shouldCopyProperties() {
        // Arrange
        OrdinateurPortable source = new OrdinateurPortable();
        source.setSystemeExploitation("Linux");
        source.setTailleEcran(17.3);
        source.setProcesseur("AMD Ryzen 9");
        source.setTailleRam(64);
        source.setTailleStockage(2000);
        source.setTypeStockage("NVMe");
        source.setCarteGraphique("RTX 4090");
        source.setAutonomieBatterie(12);

        // Act
        ordinateur.setSystemeExploitation(source.getSystemeExploitation());
        ordinateur.setTailleEcran(source.getTailleEcran());
        ordinateur.setProcesseur(source.getProcesseur());
        ordinateur.setTailleRam(source.getTailleRam());
        ordinateur.setTailleStockage(source.getTailleStockage());
        ordinateur.setTypeStockage(source.getTypeStockage());
        ordinateur.setCarteGraphique(source.getCarteGraphique());
        ordinateur.setAutonomieBatterie(source.getAutonomieBatterie());

        // Assert
        assertEquals(source.getSystemeExploitation(), ordinateur.getSystemeExploitation());
        assertEquals(source.getTailleEcran(), ordinateur.getTailleEcran());
        assertEquals(source.getProcesseur(), ordinateur.getProcesseur());
        assertEquals(source.getTailleRam(), ordinateur.getTailleRam());
        assertEquals(source.getTailleStockage(), ordinateur.getTailleStockage());
        assertEquals(source.getTypeStockage(), ordinateur.getTypeStockage());
        assertEquals(source.getCarteGraphique(), ordinateur.getCarteGraphique());
        assertEquals(source.getAutonomieBatterie(), ordinateur.getAutonomieBatterie());
    }

    @Test
    @DisplayName("Devrait gérer les valeurs extrêmes")
    void shouldHandleExtremeValues() {
        // Arrange
        Double maxDouble = Double.MAX_VALUE;
        Integer maxInteger = Integer.MAX_VALUE;

        // Act
        ordinateur.setTailleEcran(maxDouble);
        ordinateur.setTailleRam(maxInteger);
        ordinateur.setTailleStockage(maxInteger);
        ordinateur.setAutonomieBatterie(maxInteger);

        // Assert
        assertEquals(maxDouble, ordinateur.getTailleEcran());
        assertEquals(maxInteger, ordinateur.getTailleRam());
        assertEquals(maxInteger, ordinateur.getTailleStockage());
        assertEquals(maxInteger, ordinateur.getAutonomieBatterie());
    }

    @Test
    @DisplayName("Devrait vérifier l'égalité avec tous les champs")
    void shouldCheckEqualityWithAllFields() {
        // Arrange
        OrdinateurPortable ordinateur1 = new OrdinateurPortable();
        OrdinateurPortable ordinateur2 = new OrdinateurPortable();
        
        // Configuration complète des deux ordinateurs
        ordinateur1.setSystemeExploitation("Windows 11");
        ordinateur1.setTailleEcran(15.6);
        ordinateur1.setProcesseur("Intel i7");
        ordinateur1.setTailleRam(16);
        ordinateur1.setTailleStockage(512);
        ordinateur1.setTypeStockage("SSD");
        ordinateur1.setCarteGraphique("RTX 3060");
        ordinateur1.setAutonomieBatterie(8);
        
        ordinateur2.setSystemeExploitation("Windows 11");
        ordinateur2.setTailleEcran(15.6);
        ordinateur2.setProcesseur("Intel i7");
        ordinateur2.setTailleRam(16);
        ordinateur2.setTailleStockage(512);
        ordinateur2.setTypeStockage("SSD");
        ordinateur2.setCarteGraphique("RTX 3060");
        ordinateur2.setAutonomieBatterie(8);
        
        // Act & Assert
        assertEquals(ordinateur1, ordinateur2);
        assertEquals(ordinateur1.hashCode(), ordinateur2.hashCode());
        
        // Modifier un seul champ
        ordinateur2.setSystemeExploitation("Linux");
        assertNotEquals(ordinateur1, ordinateur2);
    }
} 