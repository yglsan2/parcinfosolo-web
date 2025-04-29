package com.parcinfo.web.controller;

import com.parcinfo.web.model.Appareil;
import com.parcinfo.web.service.AppareilService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests unitaires pour le AppareilController.
 * Vérifie le bon fonctionnement des opérations CRUD sur les appareils.
 */
@WebMvcTest(AppareilController.class)
public class AppareilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppareilService appareilService;

    private Appareil testAppareil;

    /**
     * Configuration initiale avant chaque test.
     * Crée un appareil de test et configure le comportement du service mock.
     */
    @BeforeEach
    public void setup() {
        testAppareil = new Appareil();
        testAppareil.setId(1L);
        testAppareil.setNom("Test Appareil");
        testAppareil.setDescription("Description de test");
        testAppareil.setType("Type de test");
        testAppareil.setEtat("État de test");

        when(appareilService.findAll()).thenReturn(Arrays.asList(testAppareil));
        when(appareilService.findById(1L)).thenReturn(Optional.of(testAppareil));
        when(appareilService.save(any(Appareil.class))).thenReturn(testAppareil);
    }

    /**
     * Test de l'endpoint list (/appareils).
     * Vérifie que la liste des appareils est correctement affichée.
     */
    @Test
    public void testListAppareils() throws Exception {
        mockMvc.perform(get("/appareils"))
                .andExpect(status().isOk())
                .andExpect(view().name("appareils/list"))
                .andExpect(model().attributeExists("appareils"));
    }

    /**
     * Test de l'endpoint new (/appareils/new).
     * Vérifie que le formulaire de création est correctement affiché.
     */
    @Test
    public void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/appareils/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("appareils/form"))
                .andExpect(model().attributeExists("appareil"));
    }

    /**
     * Test de l'endpoint view (/appareils/{id}).
     * Vérifie que les détails d'un appareil sont correctement affichés.
     */
    @Test
    public void testViewAppareil() throws Exception {
        mockMvc.perform(get("/appareils/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("appareils/view"))
                .andExpect(model().attributeExists("appareil"));
    }

    /**
     * Test de l'endpoint edit (/appareils/{id}/edit).
     * Vérifie que le formulaire d'édition est correctement affiché.
     */
    @Test
    public void testShowEditForm() throws Exception {
        mockMvc.perform(get("/appareils/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("appareils/form"))
                .andExpect(model().attributeExists("appareil"));
    }

    /**
     * Test de la création d'un appareil (POST /appareils).
     * Vérifie que l'appareil est correctement créé et que l'utilisateur est redirigé.
     */
    @Test
    public void testCreateAppareil() throws Exception {
        mockMvc.perform(post("/appareils")
                .param("nom", "Nouvel Appareil")
                .param("description", "Description")
                .param("type", "Type")
                .param("etat", "État"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/appareils"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(flash().attributeExists("messageType"));
    }

    /**
     * Test de la mise à jour d'un appareil (POST /appareils/{id}).
     * Vérifie que l'appareil est correctement mis à jour et que l'utilisateur est redirigé.
     */
    @Test
    public void testUpdateAppareil() throws Exception {
        mockMvc.perform(post("/appareils/1")
                .param("nom", "Appareil Modifié")
                .param("description", "Nouvelle Description")
                .param("type", "Nouveau Type")
                .param("etat", "Nouvel État"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/appareils"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(flash().attributeExists("messageType"));
    }

    /**
     * Test de la suppression d'un appareil (GET /appareils/{id}/delete).
     * Vérifie que l'appareil est correctement supprimé et que l'utilisateur est redirigé.
     */
    @Test
    public void testDeleteAppareil() throws Exception {
        mockMvc.perform(get("/appareils/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/appareils"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(flash().attributeExists("messageType"));
    }
} 