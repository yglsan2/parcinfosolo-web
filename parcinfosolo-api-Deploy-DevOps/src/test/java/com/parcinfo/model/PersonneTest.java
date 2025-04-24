package com.parcinfo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        String firstname = "John";
        String lastname = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";

        // Act
        personne.setFirstname(firstname);
        personne.setLastname(lastname);
        personne.setEmail(email);
        personne.setPassword(password);

        // Assert
        assertEquals(firstname, personne.getFirstname());
        assertEquals(lastname, personne.getLastname());
        assertEquals(email, personne.getEmail());
        assertEquals(password, personne.getPassword());
    }

    @Test
    @DisplayName("Devrait gérer le rôle de la personne")
    void shouldHandleRole() {
        // Arrange
        Role userRole = new Role(RoleType.USER);
        
        // Act
        personne.setRole(userRole);

        // Assert
        assertNotNull(personne.getRole());
        assertEquals(userRole, personne.getRole());
        assertEquals(RoleType.USER, personne.getRole().getName());
    }

    @Test
    @DisplayName("Devrait comparer correctement deux personnes")
    void shouldComparePersonnes() {
        // Arrange
        Personne personne1 = new Personne();
        Personne personne2 = new Personne();
        
        personne1.setFirstname("John");
        personne1.setLastname("Doe");
        personne1.setEmail("john.doe@example.com");
        
        personne2.setFirstname("John");
        personne2.setLastname("Doe");
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
        assertNull(personne.getFirstname());
        assertNull(personne.getLastname());
        assertNull(personne.getEmail());
        assertNull(personne.getPassword());
        assertNull(personne.getRole());
    }

    @Test
    @DisplayName("Devrait mettre à jour les informations de la personne")
    void shouldUpdatePersonneInfo() {
        // Arrange
        personne.setFirstname("John");
        personne.setLastname("Doe");
        personne.setEmail("john.doe@example.com");
        personne.setPassword("oldPassword");

        // Act
        personne.setFirstname("Jane");
        personne.setLastname("Smith");
        personne.setEmail("jane.smith@example.com");
        personne.setPassword("newPassword");

        // Assert
        assertEquals("Jane", personne.getFirstname());
        assertEquals("Smith", personne.getLastname());
        assertEquals("jane.smith@example.com", personne.getEmail());
        assertEquals("newPassword", personne.getPassword());
    }
} 