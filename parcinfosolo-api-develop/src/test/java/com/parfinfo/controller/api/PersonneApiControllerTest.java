package com.parfinfo.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parfinfo.dto.personne.*;
import com.parfinfo.service.PersonneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonneController.class)
public class PersonneApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonneService personneService;

    @Autowired
    private ObjectMapper objectMapper;

    private CreatePersonneRequest createRequest;
    private UpdatePersonneRequest updateRequest;
    private PersonneResponse personneResponse;
    private PersonneDetailResponse detailResponse;

    @BeforeEach
    void setUp() {
        // Initialisation des objets de test
        createRequest = new CreatePersonneRequest();
        createRequest.setNom("Dupont");
        createRequest.setPrenom("Jean");
        createRequest.setEmail("jean.dupont@example.com");
        createRequest.setDepartement("IT");
        createRequest.setStatut("Actif");

        updateRequest = new UpdatePersonneRequest();
        updateRequest.setNom("Dupont");
        updateRequest.setPrenom("Jean");
        updateRequest.setEmail("jean.dupont@example.com");
        updateRequest.setDepartement("RH");
        updateRequest.setStatut("Actif");

        personneResponse = new PersonneResponse();
        personneResponse.setId(1L);
        personneResponse.setNom("Dupont");
        personneResponse.setPrenom("Jean");
        personneResponse.setEmail("jean.dupont@example.com");
        personneResponse.setDepartement("IT");
        personneResponse.setStatut("Actif");

        detailResponse = new PersonneDetailResponse();
        detailResponse.setId(1L);
        detailResponse.setNom("Dupont");
        detailResponse.setPrenom("Jean");
        detailResponse.setEmail("jean.dupont@example.com");
        detailResponse.setDepartement("IT");
        detailResponse.setStatut("Actif");
        // Ajouter d'autres détails si nécessaire
    }

    @Test
    void testCreatePersonne() throws Exception {
        when(personneService.createPersonne(any(CreatePersonneRequest.class)))
            .thenReturn(personneResponse);

        mockMvc.perform(post("/api/personnes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("Dupont"))
                .andExpect(jsonPath("$.prenom").value("Jean"));
    }

    @Test
    void testGetPersonneById() throws Exception {
        when(personneService.getPersonneById(1L))
            .thenReturn(detailResponse);

        mockMvc.perform(get("/api/personnes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("Dupont"));
    }

    @Test
    void testSearchPersonnes() throws Exception {
        Page<PersonneResponse> page = new PageImpl<>(
            Arrays.asList(personneResponse)
        );

        when(personneService.searchPersonnes(any(PersonneSearchRequest.class), any()))
            .thenReturn(page);

        mockMvc.perform(get("/api/personnes/search")
                .param("departement", "IT")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].departement").value("IT"));
    }

    @Test
    void testUpdatePersonne() throws Exception {
        when(personneService.updatePersonne(any(Long.class), any(UpdatePersonneRequest.class)))
            .thenReturn(personneResponse);

        mockMvc.perform(put("/api/personnes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.departement").value("IT"));
    }

    @Test
    void testDeletePersonne() throws Exception {
        mockMvc.perform(delete("/api/personnes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testExportPersonnes() throws Exception {
        PersonneExportResponse exportResponse = new PersonneExportResponse();
        exportResponse.setFileUrl("http://example.com/export.csv");

        when(personneService.exportPersonnes(any()))
            .thenReturn(exportResponse);

        mockMvc.perform(post("/api/personnes/export")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileUrl").value("http://example.com/export.csv"));
    }

    @Test
    void testGetPersonneHistory() throws Exception {
        PersonneHistoryResponse historyResponse = new PersonneHistoryResponse();
        historyResponse.setId(1L);
        // Ajouter d'autres propriétés d'historique si nécessaire

        when(personneService.getPersonneHistory(1L))
            .thenReturn(historyResponse);

        mockMvc.perform(get("/api/personnes/1/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetPersonneStatistiques() throws Exception {
        PersonneStatistiqueResponse statsResponse = new PersonneStatistiqueResponse();
        statsResponse.setTotalPersonnes(100L);
        // Ajouter d'autres statistiques si nécessaire

        when(personneService.getPersonneStatistiques())
            .thenReturn(statsResponse);

        mockMvc.perform(get("/api/personnes/statistiques"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPersonnes").value(100L));
    }
} 