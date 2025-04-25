package com.parcinfo.web.controller;

import com.parcinfo.controller.PersonneController;
import com.parcinfo.model.Personne;
import com.parcinfo.service.PersonneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonneController.class)
public class PersonneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonneService personneService;

    @Test
    public void testListePersonnes() throws Exception {
        Personne personne = new Personne();
        personne.setId(1L);
        personne.setNom("Dupont");
        personne.setPrenom("Jean");

        when(personneService.findAll()).thenReturn(Arrays.asList(personne));

        mockMvc.perform(get("/personnes"))
                .andExpect(status().isOk())
                .andExpect(view().name("personnes/list"))
                .andExpect(model().attributeExists("personnes"));
    }

    @Test
    public void testDetailPersonne() throws Exception {
        Personne personne = new Personne();
        personne.setId(1L);
        personne.setNom("Dupont");
        personne.setPrenom("Jean");

        when(personneService.findById(1L)).thenReturn(Optional.of(personne));

        mockMvc.perform(get("/personnes/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("personnes/view"))
                .andExpect(model().attributeExists("personne"));
    }

    @Test
    public void testCreateForm() throws Exception {
        mockMvc.perform(get("/personnes/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("personnes/form"))
                .andExpect(model().attributeExists("personne"));
    }

    @Test
    public void testEditForm() throws Exception {
        Personne personne = new Personne();
        personne.setId(1L);
        personne.setNom("Dupont");
        personne.setPrenom("Jean");

        when(personneService.findById(1L)).thenReturn(Optional.of(personne));

        mockMvc.perform(get("/personnes/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("personnes/form"))
                .andExpect(model().attributeExists("personne"));
    }
} 