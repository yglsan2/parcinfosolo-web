package com.parcinfo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    @DisplayName("Devrait avoir les bonnes valeurs d'énumération")
    void shouldHaveCorrectEnumValues() {
        // Assert
        assertEquals(2, RoleType.values().length);
        assertEquals("USER", RoleType.USER.getValue());
        assertEquals("ADMIN", RoleType.ADMIN.getValue());
    }

    @Test
    @DisplayName("Devrait créer un rôle avec le bon type")
    void shouldCreateRoleWithType() {
        // Act
        Role role = new Role(RoleType.USER);
        
        // Assert
        assertEquals(RoleType.USER, role.getName());
    }

    @Test
    @DisplayName("Devrait créer un rôle avec type et description")
    void shouldCreateRoleWithTypeAndDescription() {
        // Act
        String description = "Utilisateur standard";
        Role role = new Role(RoleType.USER, description);
        
        // Assert
        assertEquals(RoleType.USER, role.getName());
        assertEquals(description, role.getDescription());
    }

    @Test
    @DisplayName("Devrait comparer correctement deux rôles")
    void shouldCompareRoles() {
        // Arrange
        Role role1 = new Role(RoleType.USER);
        Role role2 = new Role(RoleType.USER);
        Role role3 = new Role(RoleType.ADMIN);

        // Assert
        assertEquals(role1, role2);
        assertNotEquals(role1, role3);
    }
} 