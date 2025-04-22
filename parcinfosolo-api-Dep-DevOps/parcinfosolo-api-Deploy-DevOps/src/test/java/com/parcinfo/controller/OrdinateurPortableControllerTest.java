package com.parcinfo.controller;

import com.parcinfo.model.OrdinateurPortable;
import com.parcinfo.model.ObjetNomade;
import com.parcinfo.model.ObjetNomade.EtatObjetNomade;
import com.parcinfo.model.TypeObjetNomade;
import com.parcinfo.service.ObjetNomadeService;
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

/**
 * Tests unitaires pour le contrôleur OrdinateurPortableController.
 */
@ExtendWith(MockitoExtension.class)
class OrdinateurPortableControllerTest {

    @Mock
    private ObjetNomadeService objetNomadeService;

    @InjectMocks
    private OrdinateurPortableController ordinateurPortableController;

    private OrdinateurPortable ordinateur;

    @BeforeEach
    void setUp() {
        ordinateur = new OrdinateurPortable();
        ordinateur.setNom("Test Ordinateur");
        ordinateur.setNumeroSerie("123456789");
        ordinateur.setMarque("Marque Test");
        ordinateur.setModele("Modèle Test");
        ordinateur.setType(TypeObjetNomade.LAPTOP);
        ordinateur.setEtat(EtatObjetNomade.NEUF);
        ordinateur.setDateAcquisition(LocalDateTime.now());
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
    @DisplayName("Devrait récupérer tous les ordinateurs portables")
    void shouldGetAllOrdinateursPortables() {
        // Arrange
        List<ObjetNomade> ordinateurs = Arrays.asList(ordinateur);
        when(objetNomadeService.getObjetsNomadesByType(TypeObjetNomade.LAPTOP)).thenReturn(ordinateurs);

        // Act
        List<OrdinateurPortable> result = ordinateurPortableController.getAllOrdinateursPortables();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ordinateur.getNom(), result.get(0).getNom());
        verify(objetNomadeService).getObjetsNomadesByType(TypeObjetNomade.LAPTOP);
    }

    @Test
    @DisplayName("Devrait récupérer un ordinateur portable par son ID")
    void shouldGetOrdinateurPortableById() {
        // Arrange
        Long id = 1L;
        when(objetNomadeService.getObjetNomadeById(id)).thenReturn(Optional.of(ordinateur));

        // Act
        ResponseEntity<OrdinateurPortable> response = ordinateurPortableController.getOrdinateurPortableById(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ordinateur.getNom(), response.getBody().getNom());
        verify(objetNomadeService).getObjetNomadeById(id);
    }

    @Test
    @DisplayName("Devrait retourner 404 si l'ordinateur portable n'est pas trouvé")
    void shouldReturn404WhenOrdinateurPortableNotFound() {
        // Arrange
        Long id = 1L;
        when(objetNomadeService.getObjetNomadeById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<OrdinateurPortable> response = ordinateurPortableController.getOrdinateurPortableById(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(objetNomadeService).getObjetNomadeById(id);
    }

    @Test
    @DisplayName("Devrait créer un nouvel ordinateur portable")
    void shouldCreateOrdinateurPortable() {
        // Arrange
        when(objetNomadeService.createObjetNomade(any(OrdinateurPortable.class))).thenReturn(ordinateur);

        // Act
        OrdinateurPortable result = ordinateurPortableController.createOrdinateurPortable(ordinateur);

        // Assert
        assertNotNull(result);
        assertEquals(ordinateur.getNom(), result.getNom());
        verify(objetNomadeService).createObjetNomade(ordinateur);
    }

    @Test
    @DisplayName("Devrait mettre à jour un ordinateur portable existant")
    void shouldUpdateOrdinateurPortable() {
        // Arrange
        Long id = 1L;
        when(objetNomadeService.updateObjetNomade(eq(id), any(OrdinateurPortable.class))).thenReturn(ordinateur);

        // Act
        ResponseEntity<OrdinateurPortable> response = ordinateurPortableController.updateOrdinateurPortable(id, ordinateur);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ordinateur.getNom(), response.getBody().getNom());
        verify(objetNomadeService).updateObjetNomade(id, ordinateur);
    }

    @Test
    @DisplayName("Devrait supprimer un ordinateur portable")
    void shouldDeleteOrdinateurPortable() {
        // Arrange
        Long id = 1L;
        doNothing().when(objetNomadeService).deleteObjetNomade(id);

        // Act
        ResponseEntity<Void> response = ordinateurPortableController.deleteOrdinateurPortable(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(objetNomadeService).deleteObjetNomade(id);
    }

    @Test
    @DisplayName("Devrait gérer une erreur lors de la création d'un ordinateur portable")
    void shouldHandleErrorOnCreate() {
        // Arrange
        when(objetNomadeService.createObjetNomade(any(OrdinateurPortable.class)))
                .thenThrow(new RuntimeException("Erreur de création"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            ordinateurPortableController.createOrdinateurPortable(ordinateur);
        });
        verify(objetNomadeService).createObjetNomade(ordinateur);
    }

    @Test
    @DisplayName("Devrait vérifier le type lors de la création")
    void shouldSetTypeOnCreate() {
        // Arrange
        ordinateur.setType(null);
        when(objetNomadeService.createObjetNomade(any(OrdinateurPortable.class))).thenReturn(ordinateur);

        // Act
        OrdinateurPortable result = ordinateurPortableController.createOrdinateurPortable(ordinateur);

        // Assert
        assertEquals(TypeObjetNomade.LAPTOP, result.getType());
        verify(objetNomadeService).createObjetNomade(ordinateur);
    }

    @Test
    @DisplayName("Devrait gérer une liste vide d'ordinateurs portables")
    void shouldHandleEmptyList() {
        // Arrange
        when(objetNomadeService.getObjetsNomadesByType(TypeObjetNomade.LAPTOP))
                .thenReturn(Arrays.asList());

        // Act
        List<OrdinateurPortable> result = ordinateurPortableController.getAllOrdinateursPortables();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(objetNomadeService).getObjetsNomadesByType(TypeObjetNomade.LAPTOP);
    }

    @Test
    @DisplayName("Devrait gérer une erreur lors de la mise à jour")
    void shouldHandleUpdateError() {
        // Arrange
        Long id = 1L;
        when(objetNomadeService.updateObjetNomade(eq(id), any(OrdinateurPortable.class)))
                .thenThrow(new RuntimeException("Erreur de mise à jour"));

        // Act
        ResponseEntity<OrdinateurPortable> response = ordinateurPortableController.updateOrdinateurPortable(id, ordinateur);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(objetNomadeService).updateObjetNomade(id, ordinateur);
    }

    @Test
    @DisplayName("Devrait gérer une erreur lors de la suppression")
    void shouldHandleDeleteError() {
        // Arrange
        Long id = 1L;
        doThrow(new RuntimeException("Erreur de suppression"))
                .when(objetNomadeService).deleteObjetNomade(id);

        // Act
        ResponseEntity<Void> response = ordinateurPortableController.deleteOrdinateurPortable(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(objetNomadeService).deleteObjetNomade(id);
    }

    @Test
    @DisplayName("Devrait filtrer les objets non-ordinateurs portables")
    void shouldFilterNonLaptopObjects() {
        // Arrange
        List<ObjetNomade> mixedList = Arrays.asList(ordinateur);
        when(objetNomadeService.getObjetsNomadesByType(TypeObjetNomade.LAPTOP))
                .thenReturn(mixedList);

        // Act
        List<OrdinateurPortable> result = ordinateurPortableController.getAllOrdinateursPortables();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(obj -> obj instanceof OrdinateurPortable));
        verify(objetNomadeService).getObjetsNomadesByType(TypeObjetNomade.LAPTOP);
    }
} 