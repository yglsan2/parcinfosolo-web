package com.parcinfo.controller;

import com.parcinfo.model.Peripherique;
import com.parcinfo.model.TypePeripherique;
import com.parcinfo.service.PeripheriqueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeripheriqueControllerTest {

    @Mock
    private PeripheriqueService peripheriqueService;

    @InjectMocks
    private PeripheriqueController peripheriqueController;

    private Peripherique peripherique;

    @BeforeEach
    void setUp() {
        peripherique = new Peripherique();
        peripherique.setMarque("Logitech");
        peripherique.setModele("MX Master 3");
        peripherique.setNumeroSerie("LGT123456");
        peripherique.setType(TypePeripherique.SOURIS);
        peripherique.setEtat(Peripherique.EtatPeripherique.NEUF);
        peripherique.setDateAcquisition(LocalDateTime.now());
        peripherique.setCommentaire("Souris ergonomique");
    }

    @Test
    @DisplayName("Devrait récupérer tous les périphériques")
    void shouldGetAllPeripheriques() {
        // Arrange
        List<Peripherique> peripheriques = Arrays.asList(peripherique);
        when(peripheriqueService.getAllPeripheriques()).thenReturn(peripheriques);

        // Act
        List<Peripherique> result = peripheriqueController.getAllPeripheriques();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(peripherique.getMarque(), result.get(0).getMarque());
        verify(peripheriqueService).getAllPeripheriques();
    }

    @Test
    @DisplayName("Devrait récupérer un périphérique par son ID")
    void shouldGetPeripheriqueById() {
        // Arrange
        Long id = 1L;
        when(peripheriqueService.getPeripheriqueById(id)).thenReturn(Optional.of(peripherique));

        // Act
        ResponseEntity<Peripherique> response = peripheriqueController.getPeripheriqueById(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(peripherique.getMarque(), response.getBody().getMarque());
        verify(peripheriqueService).getPeripheriqueById(id);
    }

    @Test
    @DisplayName("Devrait retourner 404 si le périphérique n'est pas trouvé")
    void shouldReturn404WhenPeripheriqueNotFound() {
        // Arrange
        Long id = 1L;
        when(peripheriqueService.getPeripheriqueById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Peripherique> response = peripheriqueController.getPeripheriqueById(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(peripheriqueService).getPeripheriqueById(id);
    }

    @Test
    @DisplayName("Devrait récupérer un périphérique par son numéro de série")
    void shouldGetPeripheriqueByNumeroSerie() {
        // Arrange
        String numeroSerie = "LGT123456";
        when(peripheriqueService.getPeripheriqueByNumeroSerie(numeroSerie))
                .thenReturn(Optional.of(peripherique));

        // Act
        ResponseEntity<Peripherique> response = peripheriqueController.getPeripheriqueByNumeroSerie(numeroSerie);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(peripherique.getNumeroSerie(), response.getBody().getNumeroSerie());
        verify(peripheriqueService).getPeripheriqueByNumeroSerie(numeroSerie);
    }

    @Test
    @DisplayName("Devrait récupérer les périphériques par type")
    void shouldGetPeripheriquesByType() {
        // Arrange
        TypePeripherique type = TypePeripherique.SOURIS;
        List<Peripherique> peripheriques = Arrays.asList(peripherique);
        when(peripheriqueService.getPeripheriquesByType(type)).thenReturn(peripheriques);

        // Act
        List<Peripherique> result = peripheriqueController.getPeripheriquesByType(type);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(type, result.get(0).getType());
        verify(peripheriqueService).getPeripheriquesByType(type);
    }

    @Test
    @DisplayName("Devrait récupérer les périphériques par marque")
    void shouldGetPeripheriquesByMarque() {
        // Arrange
        String marque = "Logitech";
        List<Peripherique> peripheriques = Arrays.asList(peripherique);
        when(peripheriqueService.getPeripheriquesByMarque(marque)).thenReturn(peripheriques);

        // Act
        List<Peripherique> result = peripheriqueController.getPeripheriquesByMarque(marque);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(marque, result.get(0).getMarque());
        verify(peripheriqueService).getPeripheriquesByMarque(marque);
    }

    @Test
    @DisplayName("Devrait récupérer les périphériques actifs")
    void shouldGetPeripheriquesActifs() {
        // Arrange
        List<Peripherique> peripheriques = Arrays.asList(peripherique);
        when(peripheriqueService.getPeripheriquesActifs()).thenReturn(peripheriques);

        // Act
        List<Peripherique> result = peripheriqueController.getPeripheriquesActifs();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).isEstActif());
        verify(peripheriqueService).getPeripheriquesActifs();
    }

    @Test
    @DisplayName("Devrait créer un nouveau périphérique")
    void shouldCreatePeripherique() {
        // Arrange
        when(peripheriqueService.createPeripherique(any(Peripherique.class))).thenReturn(peripherique);

        // Act
        Peripherique result = peripheriqueController.createPeripherique(peripherique);

        // Assert
        assertNotNull(result);
        assertEquals(peripherique.getMarque(), result.getMarque());
        verify(peripheriqueService).createPeripherique(peripherique);
    }

    @Test
    @DisplayName("Devrait mettre à jour un périphérique existant")
    void shouldUpdatePeripherique() {
        // Arrange
        Long id = 1L;
        when(peripheriqueService.updatePeripherique(eq(id), any(Peripherique.class))).thenReturn(peripherique);

        // Act
        ResponseEntity<Peripherique> response = peripheriqueController.updatePeripherique(id, peripherique);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(peripherique.getMarque(), response.getBody().getMarque());
        verify(peripheriqueService).updatePeripherique(id, peripherique);
    }

    @Test
    @DisplayName("Devrait gérer une erreur lors de la mise à jour")
    void shouldHandleUpdateError() {
        // Arrange
        Long id = 1L;
        when(peripheriqueService.updatePeripherique(eq(id), any(Peripherique.class)))
                .thenThrow(new RuntimeException("Erreur de mise à jour"));

        // Act
        ResponseEntity<Peripherique> response = peripheriqueController.updatePeripherique(id, peripherique);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(peripheriqueService).updatePeripherique(id, peripherique);
    }

    @Test
    @DisplayName("Devrait supprimer un périphérique")
    void shouldDeletePeripherique() {
        // Arrange
        Long id = 1L;
        doNothing().when(peripheriqueService).deletePeripherique(id);

        // Act
        ResponseEntity<Void> response = peripheriqueController.deletePeripherique(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(peripheriqueService).deletePeripherique(id);
    }

    @Test
    @DisplayName("Devrait gérer une erreur lors de la suppression")
    void shouldHandleDeleteError() {
        // Arrange
        Long id = 1L;
        doThrow(new RuntimeException("Erreur de suppression"))
                .when(peripheriqueService).deletePeripherique(id);

        // Act
        ResponseEntity<Void> response = peripheriqueController.deletePeripherique(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(peripheriqueService).deletePeripherique(id);
    }
} 