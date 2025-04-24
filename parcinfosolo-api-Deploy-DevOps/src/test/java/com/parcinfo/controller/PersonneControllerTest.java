package com.parcinfo.controller;

import com.parcinfo.api.response.ApiResponse;
import com.parcinfo.model.Personne;
import com.parcinfo.model.Role;
import com.parcinfo.model.RoleType;
import com.parcinfo.service.PersonneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonneControllerTest {

    @Mock
    private PersonneService personneService;

    @InjectMocks
    private PersonneController personneController;

    private Personne personne;

    @BeforeEach
    void setUp() {
        personne = new Personne();
        personne.setFirstname("John");
        personne.setLastname("Doe");
        personne.setEmail("john.doe@example.com");
        personne.setPassword("password123");
        personne.setRole(new Role(RoleType.USER));
    }

    @Test
    @DisplayName("Devrait récupérer toutes les personnes")
    void shouldGetAllPersonnes() {
        // Arrange
        List<Personne> personnes = Arrays.asList(personne);
        when(personneService.findAll()).thenReturn(personnes);

        // Act
        ResponseEntity<ApiResponse<List<Personne>>> response = personneController.getAllPersonnes();

        // Assert
        assertNotNull(response);
        assertTrue(response.getBody().isSuccess());
        assertEquals(1, response.getBody().getData().size());
        verify(personneService).findAll();
    }

    @Test
    @DisplayName("Devrait récupérer une personne par son ID")
    void shouldGetPersonneById() {
        // Arrange
        Long id = 1L;
        when(personneService.findById(id)).thenReturn(Optional.of(personne));

        // Act
        ResponseEntity<ApiResponse<Personne>> response = personneController.getPersonneById(id);

        // Assert
        assertNotNull(response);
        assertTrue(response.getBody().isSuccess());
        assertEquals(personne.getEmail(), response.getBody().getData().getEmail());
        verify(personneService).findById(id);
    }

    @Test
    @DisplayName("Devrait retourner une erreur si la personne n'est pas trouvée")
    void shouldReturnErrorWhenPersonneNotFound() {
        // Arrange
        Long id = 1L;
        when(personneService.findById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ApiResponse<Personne>> response = personneController.getPersonneById(id);

        // Assert
        assertNotNull(response);
        assertFalse(response.getBody().isSuccess());
        assertEquals("Personne non trouvée", response.getBody().getMessage());
        verify(personneService).findById(id);
    }

    @Test
    @DisplayName("Devrait créer une nouvelle personne")
    void shouldCreatePersonne() {
        // Arrange
        when(personneService.save(any(Personne.class))).thenReturn(personne);

        // Act
        ResponseEntity<ApiResponse<Personne>> response = personneController.createPersonne(personne);

        // Assert
        assertNotNull(response);
        assertTrue(response.getBody().isSuccess());
        assertEquals("Personne créée avec succès", response.getBody().getMessage());
        assertEquals(personne.getEmail(), response.getBody().getData().getEmail());
        verify(personneService).save(personne);
    }

    @Test
    @DisplayName("Devrait mettre à jour une personne existante")
    void shouldUpdatePersonne() {
        // Arrange
        Long id = 1L;
        when(personneService.existsById(id)).thenReturn(true);
        when(personneService.save(any(Personne.class))).thenReturn(personne);

        // Act
        ResponseEntity<ApiResponse<Personne>> response = personneController.updatePersonne(id, personne);

        // Assert
        assertNotNull(response);
        assertTrue(response.getBody().isSuccess());
        assertEquals("Personne mise à jour avec succès", response.getBody().getMessage());
        assertEquals(personne.getEmail(), response.getBody().getData().getEmail());
        verify(personneService).existsById(id);
        verify(personneService).save(personne);
    }

    @Test
    @DisplayName("Devrait retourner une erreur lors de la mise à jour d'une personne inexistante")
    void shouldReturnErrorWhenUpdatingNonExistentPersonne() {
        // Arrange
        Long id = 1L;
        when(personneService.existsById(id)).thenReturn(false);

        // Act
        ResponseEntity<ApiResponse<Personne>> response = personneController.updatePersonne(id, personne);

        // Assert
        assertNotNull(response);
        assertFalse(response.getBody().isSuccess());
        assertEquals("Personne non trouvée", response.getBody().getMessage());
        verify(personneService).existsById(id);
        verify(personneService, never()).save(any(Personne.class));
    }
} 