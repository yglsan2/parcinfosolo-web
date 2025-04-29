package com.parcinfo.web.controller;

import com.parcinfo.web.service.PersonneService;
import com.parcinfo.web.service.OrdinateurService;
import com.parcinfo.web.service.PeripheriqueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests unitaires pour le WebController.
 * Vérifie le bon fonctionnement des endpoints de navigation et d'affichage.
 */
@WebMvcTest(WebController.class)
public class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonneService personneService;

    @MockBean
    private OrdinateurService ordinateurService;

    @MockBean
    private PeripheriqueService peripheriqueService;

    /**
     * Test de l'endpoint index (/).
     * Vérifie que la page d'accueil est correctement affichée.
     */
    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    /**
     * Test de l'endpoint login (/login).
     * Vérifie que la page de connexion est correctement affichée.
     */
    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    /**
     * Test de l'endpoint register (/register).
     * Vérifie que la page d'inscription est correctement affichée.
     */
    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    /**
     * Test de l'endpoint personnes (/personnes).
     * Vérifie que la liste des personnes est correctement affichée.
     */
    @Test
    public void testPersonnes() throws Exception {
        mockMvc.perform(get("/personnes"))
                .andExpect(status().isOk())
                .andExpect(view().name("personnes/index"))
                .andExpect(model().attributeExists("personnes"));
    }

    /**
     * Test de l'endpoint ordinateurs (/ordinateurs).
     * Vérifie que la liste des ordinateurs est correctement affichée.
     */
    @Test
    public void testOrdinateurs() throws Exception {
        mockMvc.perform(get("/ordinateurs"))
                .andExpect(status().isOk())
                .andExpect(view().name("ordinateurs/index"))
                .andExpect(model().attributeExists("ordinateurs"));
    }

    /**
     * Test de l'endpoint peripheriques (/peripheriques).
     * Vérifie que la liste des périphériques est correctement affichée.
     */
    @Test
    public void testPeripheriques() throws Exception {
        mockMvc.perform(get("/peripheriques"))
                .andExpect(status().isOk())
                .andExpect(view().name("peripheriques/index"))
                .andExpect(model().attributeExists("peripheriques"));
    }
} 