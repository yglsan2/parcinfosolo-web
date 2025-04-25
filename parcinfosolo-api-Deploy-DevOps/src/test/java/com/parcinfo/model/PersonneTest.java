package com.parcinfo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonneTest {

    private Personne personne;

    @BeforeEach
    void setUp() {
        personne = new Personne();
    }

    @Test
    @DisplayName("Devrait créer une personne avec des valeurs valides")
    void shouldCreateValidPersonne() {
        // Arrange
        String nom = "Dupont";
        String prenom = "Jean";
        String email = "jean.dupont@example.com";
        String telephone = "0123456789";
        String adresse = "123 rue Example";
        LocalDate dateNaissance = LocalDate.of(1990, 1, 1);

        // Act
        personne.setNom(nom);
        personne.setPrenom(prenom);
        personne.setEmail(email);
        personne.setTelephone(telephone);
        personne.setAdresse(adresse);
        personne.setDateNaissance(dateNaissance);

        // Assert
        assertEquals(nom, personne.getNom());
        assertEquals(prenom, personne.getPrenom());
        assertEquals(email, personne.getEmail());
        assertEquals(telephone, personne.getTelephone());
        assertEquals(adresse, personne.getAdresse());
        assertEquals(dateNaissance, personne.getDateNaissance());
    }

    @Test
    @DisplayName("Devrait gérer le rôle de la personne")
    void shouldHandleRole() {
        // Arrange
        Role role = Role.USER;
        
        // Act
        personne.setRole(role);

        // Assert
        assertNotNull(personne.getRole());
        assertEquals(role, personne.getRole());
    }

    @Test
    @DisplayName("Devrait comparer correctement deux personnes")
    void shouldComparePersonnes() {
        // Arrange
        Personne personne1 = new Personne();
        Personne personne2 = new Personne();
        
        personne1.setNom("John");
        personne1.setPrenom("Doe");
        personne1.setEmail("john.doe@example.com");
        
        personne2.setNom("John");
        personne2.setPrenom("Doe");
        personne2.setEmail("john.doe@example.com");
        
        // Act & Assert
        assertEquals(personne1, personne2);
        assertEquals(personne1.hashCode(), personne2.hashCode());
        
        // Modifier un champ
        personne2.setEmail("jane.doe@example.com");
        assertNotEquals(personne1, personne2);
    }

    @Test
    @DisplayName("Devrait gérer les valeurs nulles")
    void shouldHandleNullValues() {
        // Act & Assert
        assertNull(personne.getNom());
        assertNull(personne.getPrenom());
        assertNull(personne.getEmail());
        assertNull(personne.getTelephone());
        assertNull(personne.getAdresse());
        assertNull(personne.getDateNaissance());
        assertNull(personne.getRole());
    }

    @Test
    @DisplayName("Devrait mettre à jour les informations de la personne")
    void shouldUpdatePersonneInfo() {
        // Arrange
        personne.setNom("John");
        personne.setPrenom("Doe");
        personne.setEmail("john.doe@example.com");
        personne.setTelephone("0123456789");
        personne.setAdresse("123 rue Example");
        personne.setDateNaissance(LocalDate.of(1990, 1, 1));

        // Act
        personne.setNom("Jane");
        personne.setPrenom("Smith");
        personne.setEmail("jane.smith@example.com");
        personne.setTelephone("0123456789");
        personne.setAdresse("123 rue Example");
        personne.setDateNaissance(LocalDate.of(1990, 1, 1));

        // Assert
        assertEquals("Jane", personne.getNom());
        assertEquals("Smith", personne.getPrenom());
        assertEquals("jane.smith@example.com", personne.getEmail());
        assertEquals("0123456789", personne.getTelephone());
        assertEquals("123 rue Example", personne.getAdresse());
        assertEquals(LocalDate.of(1990, 1, 1), personne.getDateNaissance());
    }

    @Test
    void testPersonneBuilder() {
        // Given
        String nom = "Dupont";
        String prenom = "Jean";
        String email = "jean.dupont@example.com";
        String telephone = "0123456789";
        Role role = Role.USER;

        // When
        Personne personne = Personne.builder()
                .nom(nom)
                .prenom(prenom)
                .email(email)
                .telephone(telephone)
                .role(role)
                .build();

        // Then
        assertEquals(nom, personne.getNom());
        assertEquals(prenom, personne.getPrenom());
        assertEquals(email, personne.getEmail());
        assertEquals(telephone, personne.getTelephone());
        assertEquals(role, personne.getRole());
    }

    @Test
    void testPersonneEquality() {
        // Given
        Personne personne1 = new Personne();
        personne1.setNom("Dupont");
        personne1.setPrenom("Jean");
        personne1.setEmail("jean.dupont@example.com");
        personne1.setTelephone("0123456789");
        personne1.setRole(Role.USER);

        Personne personne2 = new Personne();
        personne2.setNom("Dupont");
        personne2.setPrenom("Jean");
        personne2.setEmail("jean.dupont@example.com");
        personne2.setTelephone("0123456789");
        personne2.setRole(Role.USER);

        // Then
        assertEquals(personne1, personne2);
        assertEquals(personne1.hashCode(), personne2.hashCode());
    }
} 