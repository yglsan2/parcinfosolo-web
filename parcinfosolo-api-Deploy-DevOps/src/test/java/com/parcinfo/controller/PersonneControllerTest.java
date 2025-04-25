package com.parcinfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcinfo.model.Personne;
import com.parcinfo.service.PersonneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonneController.class)
public class PersonneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonneService personneService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllPersonnes_ShouldReturnPersonnes() throws Exception {
        Personne personne = new Personne();
        personne.setId(1L);
        personne.setNom("Test");
        personne.setPrenom("User");

        when(personneService.findAll()).thenReturn(Arrays.asList(personne));

        mockMvc.perform(get("/personnes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nom").value("Test"))
                .andExpect(jsonPath("$[0].prenom").value("User"));
    }

    @Test
    public void getPersonneById_ShouldReturnPersonne() throws Exception {
        Personne personne = new Personne();
        personne.setId(1L);
        personne.setNom("Test");
        personne.setPrenom("User");

        when(personneService.findById(1L)).thenReturn(Optional.of(personne));

        mockMvc.perform(get("/personnes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nom").value("Test"))
                .andExpect(jsonPath("$.prenom").value("User"));
    }

    @Test
    public void createPersonne_ShouldReturnCreatedPersonne() throws Exception {
        Personne personne = new Personne();
        personne.setNom("Test");
        personne.setPrenom("User");

        when(personneService.save(any(Personne.class))).thenReturn(personne);

        mockMvc.perform(post("/personnes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personne)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Test"))
                .andExpect(jsonPath("$.prenom").value("User"));
    }

    @Test
    public void updatePersonne_ShouldReturnUpdatedPersonne() throws Exception {
        Personne personne = new Personne();
        personne.setId(1L);
        personne.setNom("Test");
        personne.setPrenom("User");

        when(personneService.existsById(1L)).thenReturn(true);
        when(personneService.save(any(Personne.class))).thenReturn(personne);

        mockMvc.perform(put("/personnes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personne)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nom").value("Test"))
                .andExpect(jsonPath("$.prenom").value("User"));
    }

    @Test
    public void deletePersonne_ShouldReturnNoContent() throws Exception {
        when(personneService.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/personnes/1"))
                .andExpect(status().isNoContent());
    }
} 