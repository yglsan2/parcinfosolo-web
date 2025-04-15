package com.parfinfo.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parfinfo.controller.AppareilController;
import com.parfinfo.dto.appareil.*;
import com.parfinfo.service.AppareilService;
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

@WebMvcTest(AppareilController.class)
public class AppareilApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppareilService appareilService;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateAppareilRequest createRequest;
    private UpdateAppareilRequest updateRequest;
    private AppareilResponse appareilResponse;

    @BeforeEach
    void setUp() {
        // Initialisation des objets de test
        createRequest = new CreateAppareilRequest();
        createRequest.setType("Serveur");
        createRequest.setMarque("Dell");
        createRequest.setModele("PowerEdge");
        createRequest.setNumeroSerie("123456");
        createRequest.setStatut("En service");

        updateRequest = new UpdateAppareilRequest();
        updateRequest.setType("Serveur");
        updateRequest.setMarque("Dell");
        updateRequest.setModele("PowerEdge R740");
        updateRequest.setNumeroSerie("123456");
        updateRequest.setStatut("En maintenance");

        appareilResponse = new AppareilResponse();
        appareilResponse.setId(1L);
        appareilResponse.setType("Serveur");
        appareilResponse.setMarque("Dell");
        appareilResponse.setModele("PowerEdge");
        appareilResponse.setNumeroSerie("123456");
        appareilResponse.setStatut("En service");
    }

    @Test
    void testCreateAppareil() throws Exception {
        when(appareilService.createAppareil(any(CreateAppareilRequest.class)))
            .thenReturn(appareilResponse);

        mockMvc.perform(post("/api/appareils")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("Serveur"))
                .andExpect(jsonPath("$.marque").value("Dell"));
    }

    @Test
    void testGetAppareilById() throws Exception {
        when(appareilService.getAppareilById(1L))
            .thenReturn(appareilResponse);

        mockMvc.perform(get("/api/appareils/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("Serveur"));
    }

    @Test
    void testSearchAppareils() throws Exception {
        Page<AppareilResponse> page = new PageImpl<>(
            Arrays.asList(appareilResponse)
        );

        when(appareilService.searchAppareils(any(), any()))
            .thenReturn(page);

        mockMvc.perform(get("/api/appareils/search")
                .param("type", "Serveur")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].type").value("Serveur"));
    }

    @Test
    void testUpdateAppareil() throws Exception {
        when(appareilService.updateAppareil(any(Long.class), any(UpdateAppareilRequest.class)))
            .thenReturn(appareilResponse);

        mockMvc.perform(put("/api/appareils/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("Serveur"));
    }

    @Test
    void testDeleteAppareil() throws Exception {
        mockMvc.perform(delete("/api/appareils/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAppareilTypes() throws Exception {
        when(appareilService.getAppareilTypes())
            .thenReturn(Arrays.asList("Serveur", "Switch", "Routeur"));

        mockMvc.perform(get("/api/appareils/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Serveur"))
                .andExpect(jsonPath("$[1]").value("Switch"))
                .andExpect(jsonPath("$[2]").value("Routeur"));
    }

    @Test
    void testGetAppareilStatuts() throws Exception {
        when(appareilService.getAppareilStatuts())
            .thenReturn(Arrays.asList("En service", "En maintenance", "Hors service"));

        mockMvc.perform(get("/api/appareils/statuts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("En service"))
                .andExpect(jsonPath("$[1]").value("En maintenance"))
                .andExpect(jsonPath("$[2]").value("Hors service"));
    }
} 