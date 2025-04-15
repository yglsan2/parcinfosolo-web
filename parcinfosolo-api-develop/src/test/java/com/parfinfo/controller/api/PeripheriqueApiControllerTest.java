package com.parfinfo.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parfinfo.dto.peripherique.*;
import com.parfinfo.service.PeripheriqueService;
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

@WebMvcTest(PeripheriqueController.class)
public class PeripheriqueApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeripheriqueService peripheriqueService;

    @Autowired
    private ObjectMapper objectMapper;

    private PeripheriqueRequest peripheriqueRequest;
    private PeripheriqueResponse peripheriqueResponse;
    private PeripheriqueDetailResponse peripheriqueDetailResponse;

    @BeforeEach
    void setUp() {
        // Initialisation des objets de test
        peripheriqueRequest = new PeripheriqueRequest();
        peripheriqueRequest.setType("Clavier");
        peripheriqueRequest.setMarque("Logitech");
        peripheriqueRequest.setModele("K120");
        peripheriqueRequest.setNumeroSerie("123456");
        peripheriqueRequest.setStatut("Actif");

        peripheriqueResponse = new PeripheriqueResponse();
        peripheriqueResponse.setId(1L);
        peripheriqueResponse.setType("Clavier");
        peripheriqueResponse.setMarque("Logitech");
        peripheriqueResponse.setModele("K120");
        peripheriqueResponse.setNumeroSerie("123456");
        peripheriqueResponse.setStatut("Actif");

        peripheriqueDetailResponse = new PeripheriqueDetailResponse();
        peripheriqueDetailResponse.setId(1L);
        peripheriqueDetailResponse.setType("Clavier");
        peripheriqueDetailResponse.setMarque("Logitech");
        peripheriqueDetailResponse.setModele("K120");
        peripheriqueDetailResponse.setNumeroSerie("123456");
        peripheriqueDetailResponse.setStatut("Actif");
        // Ajout des détails supplémentaires si nécessaire
    }

    @Test
    void testCreatePeripherique() throws Exception {
        when(peripheriqueService.createPeripherique(any(PeripheriqueRequest.class)))
            .thenReturn(peripheriqueResponse);

        mockMvc.perform(post("/api/peripheriques")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(peripheriqueRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("Clavier"))
                .andExpect(jsonPath("$.marque").value("Logitech"));
    }

    @Test
    void testGetPeripheriqueById() throws Exception {
        when(peripheriqueService.getPeripheriqueById(1L))
            .thenReturn(peripheriqueDetailResponse);

        mockMvc.perform(get("/api/peripheriques/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("Clavier"));
    }

    @Test
    void testSearchPeripheriques() throws Exception {
        Page<PeripheriqueResponse> page = new PageImpl<>(
            Arrays.asList(peripheriqueResponse)
        );

        when(peripheriqueService.searchPeripheriques(any(PeripheriqueSearchRequest.class), any()))
            .thenReturn(page);

        mockMvc.perform(get("/api/peripheriques/search")
                .param("type", "Clavier")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].type").value("Clavier"));
    }

    @Test
    void testUpdatePeripherique() throws Exception {
        when(peripheriqueService.updatePeripherique(any(Long.class), any(PeripheriqueRequest.class)))
            .thenReturn(peripheriqueResponse);

        mockMvc.perform(put("/api/peripheriques/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(peripheriqueRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("Clavier"));
    }

    @Test
    void testDeletePeripherique() throws Exception {
        mockMvc.perform(delete("/api/peripheriques/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testExportPeripheriques() throws Exception {
        PeripheriqueExportResponse exportResponse = new PeripheriqueExportResponse();
        exportResponse.setFileUrl("http://example.com/export.csv");

        when(peripheriqueService.exportPeripheriques(any()))
            .thenReturn(exportResponse);

        mockMvc.perform(post("/api/peripheriques/export")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileUrl").value("http://example.com/export.csv"));
    }

    @Test
    void testGetPeripheriqueHistory() throws Exception {
        PeripheriqueHistoryResponse historyResponse = new PeripheriqueHistoryResponse();
        historyResponse.setId(1L);
        // Ajouter d'autres propriétés d'historique si nécessaire

        when(peripheriqueService.getPeripheriqueHistory(1L))
            .thenReturn(historyResponse);

        mockMvc.perform(get("/api/peripheriques/1/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
} 