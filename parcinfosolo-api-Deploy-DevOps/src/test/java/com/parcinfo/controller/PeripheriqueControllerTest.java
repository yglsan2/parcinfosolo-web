package com.parcinfo.controller;

import com.parcinfo.model.Peripherique;
import com.parcinfo.model.TypePeripherique;
import com.parcinfo.web.service.PeripheriqueService;
import com.parcinfo.web.controller.PeripheriqueController;
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
        testPeripherique.setId(1L);
        testPeripherique.setType(TypePeripherique.SOURIS);
        testPeripherique.setMarque("Test Marque");
        testPeripherique.setModele("Test Modele");
        testPeripherique.setNumeroSerie("Test NumeroSerie");
        testPeripherique.setDateAcquisition(LocalDateTime.now());
        testPeripherique.setEtat(Peripherique.EtatPeripherique.BON);
        testPeripheriques = Arrays.asList(testPeripherique);
    }

    @Test
    void listPeripheriques_ShouldReturnListView() {
        when(peripheriqueService.findAll()).thenReturn(testPeripheriques);

        String viewName = peripheriqueController.listPeripheriques(model);
        
        assertEquals("peripheriques/list", viewName);
        verify(model).addAttribute("peripheriques", testPeripheriques);
    }

    @Test
    void viewPeripherique_WhenExists_ShouldReturnView() {
        when(peripheriqueService.findById(1L)).thenReturn(Optional.of(testPeripherique));

        String viewName = peripheriqueController.viewPeripherique(1L, model, redirectAttributes);
        
        assertEquals("peripheriques/view", viewName);
        verify(model).addAttribute("peripherique", testPeripherique);
    }

    @Test
    void viewPeripherique_WhenNotExists_ShouldRedirectToList() {
        when(peripheriqueService.findById(1L)).thenReturn(Optional.empty());

        String viewName = peripheriqueController.viewPeripherique(1L, model, redirectAttributes);
        
        assertEquals("redirect:/peripheriques", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Périphérique non trouvé");
    }

    @Test
    void showCreateForm_ShouldReturnFormView() {
        String viewName = peripheriqueController.showCreateForm(model);
        
        assertEquals("peripheriques/form", viewName);
        verify(model).addAttribute(eq("peripherique"), any(Peripherique.class));
    }

    @Test
    void showEditForm_WhenExists_ShouldReturnFormView() {
        when(peripheriqueService.findById(1L)).thenReturn(Optional.of(testPeripherique));

        String viewName = peripheriqueController.showEditForm(1L, model, redirectAttributes);
        
        assertEquals("peripheriques/form", viewName);
        verify(model).addAttribute("peripherique", testPeripherique);
    }

    @Test
    void showEditForm_WhenNotExists_ShouldRedirectToList() {
        when(peripheriqueService.findById(1L)).thenReturn(Optional.empty());

        String viewName = peripheriqueController.showEditForm(1L, model, redirectAttributes);
        
        assertEquals("redirect:/peripheriques", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Périphérique non trouvé");
    }

    @Test
    void savePeripherique_WhenValid_ShouldRedirectToList() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(peripheriqueService.save(any(Peripherique.class))).thenReturn(testPeripherique);

        String viewName = peripheriqueController.savePeripherique(testPeripherique, bindingResult, redirectAttributes);
        
        assertEquals("redirect:/peripheriques", viewName);
        verify(redirectAttributes).addFlashAttribute("success", "Périphérique créé avec succès");
    }

    @Test
    void savePeripherique_WhenInvalid_ShouldReturnFormView() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = peripheriqueController.savePeripherique(testPeripherique, bindingResult, redirectAttributes);
        
        assertEquals("peripheriques/form", viewName);
    }

    @Test
    void updatePeripherique_WhenValid_ShouldRedirectToList() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(peripheriqueService.findById(1L)).thenReturn(Optional.of(testPeripherique));
        when(peripheriqueService.save(any(Peripherique.class))).thenReturn(testPeripherique);

        String viewName = peripheriqueController.updatePeripherique(1L, testPeripherique, bindingResult, redirectAttributes);
        
        assertEquals("redirect:/peripheriques", viewName);
        verify(redirectAttributes).addFlashAttribute("success", "Périphérique mis à jour avec succès");
    }

    @Test
    void updatePeripherique_WhenInvalid_ShouldReturnFormView() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = peripheriqueController.updatePeripherique(1L, testPeripherique, bindingResult, redirectAttributes);
        
        assertEquals("peripheriques/form", viewName);
    }

    @Test
    void deletePeripherique_WhenSuccess_ShouldRedirectToList() {
        when(peripheriqueService.findById(1L)).thenReturn(Optional.of(testPeripherique));

        String viewName = peripheriqueController.deletePeripherique(1L, redirectAttributes);
        
        assertEquals("redirect:/peripheriques", viewName);
        verify(redirectAttributes).addFlashAttribute("success", "Périphérique supprimé avec succès");
    }

    @Test
    void deletePeripherique_WhenNotExists_ShouldRedirectToList() {
        when(peripheriqueService.findById(1L)).thenReturn(Optional.empty());

        String viewName = peripheriqueController.deletePeripherique(1L, redirectAttributes);
        
        assertEquals("redirect:/peripheriques", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Périphérique non trouvé");
    }
} 