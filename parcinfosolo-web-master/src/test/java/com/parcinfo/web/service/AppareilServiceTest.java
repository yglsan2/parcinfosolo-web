package com.parcinfo.web.service;

import com.parcinfo.web.model.Appareil;
import com.parcinfo.web.repository.AppareilRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour le AppareilService.
 * Vérifie le bon fonctionnement des opérations CRUD sur les appareils.
 */
@ExtendWith(MockitoExtension.class)
public class AppareilServiceTest {

    @Mock
    private AppareilRepository appareilRepository;

    @InjectMocks
    private AppareilService appareilService;

    private Appareil testAppareil;

    /**
     * Configuration initiale avant chaque test.
     * Crée un appareil de test.
     */
    @BeforeEach
    public void setup() {
        testAppareil = new Appareil();
        testAppareil.setId(1L);
        testAppareil.setNom("Test Appareil");
        testAppareil.setDescription("Description de test");
        testAppareil.setType("Type de test");
        testAppareil.setEtat("État de test");
    }

    /**
     * Test de la méthode findAll().
     * Vérifie que tous les appareils sont correctement récupérés.
     */
    @Test
    public void testFindAll() {
        when(appareilRepository.findAll()).thenReturn(Arrays.asList(testAppareil));

        List<Appareil> result = appareilService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testAppareil, result.get(0));
        verify(appareilRepository, times(1)).findAll();
    }

    /**
     * Test de la méthode findById() avec un ID existant.
     * Vérifie que l'appareil est correctement récupéré.
     */
    @Test
    public void testFindById_Success() {
        when(appareilRepository.findById(1L)).thenReturn(Optional.of(testAppareil));

        Appareil result = appareilService.findById(1L);

        assertNotNull(result);
        assertEquals(testAppareil, result);
        verify(appareilRepository, times(1)).findById(1L);
    }

    /**
     * Test de la méthode findById() avec un ID inexistant.
     * Vérifie que l'exception appropriée est levée.
     */
    @Test
    public void testFindById_NotFound() {
        when(appareilRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> appareilService.findById(1L));
        verify(appareilRepository, times(1)).findById(1L);
    }

    /**
     * Test de la méthode save().
     * Vérifie que l'appareil est correctement sauvegardé.
     */
    @Test
    public void testSave() {
        when(appareilRepository.save(any(Appareil.class))).thenReturn(testAppareil);

        Appareil result = appareilService.save(testAppareil);

        assertNotNull(result);
        assertEquals(testAppareil, result);
        verify(appareilRepository, times(1)).save(testAppareil);
    }

    /**
     * Test de la méthode deleteById().
     * Vérifie que l'appareil est correctement supprimé.
     */
    @Test
    public void testDeleteById() {
        doNothing().when(appareilRepository).deleteById(1L);

        appareilService.deleteById(1L);

        verify(appareilRepository, times(1)).deleteById(1L);
    }
} 