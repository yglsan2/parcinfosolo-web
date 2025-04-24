package com.parcinfo.controller;

import com.parcinfo.model.Peripherique;
import com.parcinfo.model.TypePeripherique;
import com.parcinfo.service.PeripheriqueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class PeripheriqueControllerTest {

    @Mock
    private PeripheriqueService peripheriqueService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private PeripheriqueController peripheriqueController;

    private Peripherique testPeripherique;
    private List<Peripherique> testPeripheriques;

    @BeforeEach
    void setUp() {
        testPeripherique = new Peripherique();
        testPeripherique.setIdPeripherique(1L);
        testPeripherique.setType(TypePeripherique.SOURIS);
        testPeripherique.setMarque("Test Marque");
        testPeripherique.setModele("Test Modele");
        testPeripherique.setNumeroSerie("Test NumeroSerie");
        testPeripherique.setDateAcquisition(LocalDateTime.now());
        testPeripherique.setEtat(Peripherique.EtatPeripherique.BON);
        testPeripheriques = Arrays.asList(testPeripherique);
    }

    @Test
    void list_ShouldReturnListView() {
        when(peripheriqueService.findAll()).thenReturn(testPeripheriques);

        String viewName = peripheriqueController.list(model);
        
        assertEquals("peripheriques/list", viewName);
        verify(model).addAttribute("peripheriques", testPeripheriques);
    }

    @Test
    void view_WhenExists_ShouldReturnView() {
        when(peripheriqueService.findById(1L)).thenReturn(Optional.of(testPeripherique));

        String viewName = peripheriqueController.view(1L, model);
        
        assertEquals("peripheriques/view", viewName);
        verify(model).addAttribute("peripherique", testPeripherique);
    }

    @Test
    void view_WhenNotExists_ShouldThrowException() {
        when(peripheriqueService.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> {
            peripheriqueController.view(1L, model);
        });
    }

    @Test
    void showCreateForm_ShouldReturnFormView() {
        String viewName = peripheriqueController.showCreateForm(model);
        
        assertEquals("peripheriques/form", viewName);
        verify(model).addAttribute(eq("peripherique"), any(Peripherique.class));
        verify(model).addAttribute("mode", "create");
    }

    @Test
    void showEditForm_WhenExists_ShouldReturnFormView() {
        when(peripheriqueService.findById(1L)).thenReturn(Optional.of(testPeripherique));

        String viewName = peripheriqueController.showEditForm(1L, model);
        
        assertEquals("peripheriques/form", viewName);
        verify(model).addAttribute("peripherique", testPeripherique);
        verify(model).addAttribute("mode", "edit");
    }

    @Test
    void showEditForm_WhenNotExists_ShouldThrowException() {
        when(peripheriqueService.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> {
            peripheriqueController.showEditForm(1L, model);
        });
    }

    @Test
    void save_WhenValid_ShouldRedirectToList() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(peripheriqueService.save(any(Peripherique.class))).thenReturn(testPeripherique);

        String viewName = peripheriqueController.save(testPeripherique, bindingResult, redirectAttributes);
        
        assertEquals("redirect:/peripheriques", viewName);
        verify(redirectAttributes).addFlashAttribute("message", "Le périphérique a été enregistré avec succès.");
    }

    @Test
    void save_WhenInvalid_ShouldReturnFormView() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = peripheriqueController.save(testPeripherique, bindingResult, redirectAttributes);
        
        assertEquals("peripheriques/form", viewName);
    }

    @Test
    void delete_WhenSuccess_ShouldRedirectToList() {
        when(peripheriqueService.findById(1L)).thenReturn(Optional.of(testPeripherique));
        doNothing().when(peripheriqueService).delete(any(Peripherique.class));

        String viewName = peripheriqueController.delete(1L, redirectAttributes);
        
        assertEquals("redirect:/peripheriques", viewName);
        verify(redirectAttributes).addFlashAttribute("message", "Le périphérique a été supprimé avec succès.");
    }

    @Test
    void delete_WhenNotExists_ShouldThrowException() {
        when(peripheriqueService.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> {
            peripheriqueController.delete(1L, redirectAttributes);
        });
    }
} 