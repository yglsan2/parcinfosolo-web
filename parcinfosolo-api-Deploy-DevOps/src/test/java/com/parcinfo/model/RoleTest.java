package com.parcinfo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void testRoleValues() {
        assertEquals(2, Role.values().length);
        assertTrue(containsRole(Role.values(), Role.USER));
        assertTrue(containsRole(Role.values(), Role.ADMIN));
    }

    @Test
    void testRoleNames() {
        assertEquals("USER", Role.USER.name());
        assertEquals("ADMIN", Role.ADMIN.name());
    }

    private boolean containsRole(Role[] roles, Role roleToFind) {
        for (Role role : roles) {
            if (role == roleToFind) {
                return true;
            }
        }
        return false;
    }
} 