package com.parcinfo.web.controller;

import com.parcinfo.controller.PeripheriqueController;
import com.parcinfo.model.Peripherique;
import com.parcinfo.model.TypePeripherique;
import com.parcinfo.service.PeripheriqueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PeripheriqueController.class)
public class PeripheriqueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeripheriqueService peripheriqueService;

    @Test
    public void testListePeripheriques() throws Exception {
        Peripherique peripherique = new Peripherique();
        peripherique.setId(1L);
        peripherique.setMarque("HP");
        peripherique.setModele("LaserJet");

        when(peripheriqueService.findAll()).thenReturn(Arrays.asList(peripherique));

        mockMvc.perform(get("/peripheriques"))
                .andExpect(status().isOk())
                .andExpect(view().name("peripheriques/list"))
                .andExpect(model().attributeExists("peripheriques"));
    }

    @Test
    public void testDetailPeripherique() throws Exception {
        Peripherique peripherique = new Peripherique();
        peripherique.setId(1L);
        peripherique.setMarque("HP");
        peripherique.setModele("LaserJet");

        when(peripheriqueService.findById(1L)).thenReturn(Optional.of(peripherique));

        mockMvc.perform(get("/peripheriques/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("peripheriques/view"))
                .andExpect(model().attributeExists("peripherique"));
    }

    @Test
    public void testCreateForm() throws Exception {
        mockMvc.perform(get("/peripheriques/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("peripheriques/form"))
                .andExpect(model().attributeExists("peripherique"));
    }

    @Test
    public void testEditForm() throws Exception {
        Peripherique peripherique = new Peripherique();
        peripherique.setId(1L);
        peripherique.setMarque("HP");
        peripherique.setModele("LaserJet");

        when(peripheriqueService.findById(1L)).thenReturn(Optional.of(peripherique));

        mockMvc.perform(get("/peripheriques/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("peripheriques/form"))
                .andExpect(model().attributeExists("peripherique"));
    }
} 