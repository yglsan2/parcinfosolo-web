package com.parcinfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcinfo.config.TestSecurityConfig;
import com.parcinfo.model.Peripherique;
import com.parcinfo.model.TypePeripherique;
import com.parcinfo.service.PeripheriqueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PeripheriqueController.class)
@Import(TestSecurityConfig.class)
public class PeripheriqueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeripheriqueService peripheriqueService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllPeripheriques() throws Exception {
        Peripherique peripherique = new Peripherique();
        peripherique.setId(1L);
        peripherique.setMarque("HP");
        peripherique.setModele("LaserJet");
        peripherique.setType(TypePeripherique.IMPRIMANTE);
        peripherique.setNumeroSerie("123456");
        peripherique.setDateAcquisition(LocalDateTime.now());

        when(peripheriqueService.findAll()).thenReturn(Arrays.asList(peripherique));

        mockMvc.perform(get("/api/peripheriques"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].marque").value("HP"))
                .andExpect(jsonPath("$[0].modele").value("LaserJet"))
                .andExpect(jsonPath("$[0].type").value("IMPRIMANTE"))
                .andExpect(jsonPath("$[0].numeroSerie").value("123456"));
    }

    @Test
    public void testGetPeripheriqueById() throws Exception {
        Peripherique peripherique = new Peripherique();
        peripherique.setId(1L);
        peripherique.setMarque("HP");
        peripherique.setModele("LaserJet");
        peripherique.setType(TypePeripherique.IMPRIMANTE);
        peripherique.setNumeroSerie("123456");
        peripherique.setDateAcquisition(LocalDateTime.now());

        when(peripheriqueService.findById(1L)).thenReturn(Optional.of(peripherique));

        mockMvc.perform(get("/api/peripheriques/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.marque").value("HP"))
                .andExpect(jsonPath("$.modele").value("LaserJet"))
                .andExpect(jsonPath("$.type").value("IMPRIMANTE"))
                .andExpect(jsonPath("$.numeroSerie").value("123456"));
    }

    @Test
    public void testCreatePeripherique() throws Exception {
        Peripherique peripherique = new Peripherique();
        peripherique.setMarque("HP");
        peripherique.setModele("LaserJet");
        peripherique.setType(TypePeripherique.IMPRIMANTE);
        peripherique.setNumeroSerie("123456");
        peripherique.setDateAcquisition(LocalDateTime.now());

        when(peripheriqueService.save(any(Peripherique.class))).thenReturn(peripherique);

        mockMvc.perform(post("/api/peripheriques")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(peripherique)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.marque").value("HP"))
                .andExpect(jsonPath("$.modele").value("LaserJet"))
                .andExpect(jsonPath("$.type").value("IMPRIMANTE"))
                .andExpect(jsonPath("$.numeroSerie").value("123456"));
    }

    @Test
    public void testUpdatePeripherique() throws Exception {
        Peripherique peripherique = new Peripherique();
        peripherique.setId(1L);
        peripherique.setMarque("HP");
        peripherique.setModele("LaserJet");
        peripherique.setType(TypePeripherique.IMPRIMANTE);
        peripherique.setNumeroSerie("123456");
        peripherique.setDateAcquisition(LocalDateTime.now());

        when(peripheriqueService.findById(1L)).thenReturn(Optional.of(peripherique));
        when(peripheriqueService.save(any(Peripherique.class))).thenReturn(peripherique);

        mockMvc.perform(put("/api/peripheriques/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(peripherique)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.marque").value("HP"))
                .andExpect(jsonPath("$.modele").value("LaserJet"))
                .andExpect(jsonPath("$.type").value("IMPRIMANTE"))
                .andExpect(jsonPath("$.numeroSerie").value("123456"));
    }

    @Test
    public void testDeletePeripherique() throws Exception {
        mockMvc.perform(delete("/api/peripheriques/1"))
                .andExpect(status().isNoContent());
    }
} 