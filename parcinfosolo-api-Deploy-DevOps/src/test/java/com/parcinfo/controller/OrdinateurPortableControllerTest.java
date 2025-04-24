package com.parcinfo.controller;

import com.parcinfo.model.OrdinateurPortable;
import com.parcinfo.model.TypeObjetNomade;
import com.parcinfo.service.ObjetNomadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour le contrôleur OrdinateurPortableController.
 */
@SpringBootTest
class OrdinateurPortableControllerTest {

    @Mock
    private ObjetNomadeService objetNomadeService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private OrdinateurPortableController ordinateurPortableController;

    private OrdinateurPortable testOrdinateur;
    private List<OrdinateurPortable> testOrdinateurs;

    @BeforeEach
    void setUp() {
        testOrdinateur = new OrdinateurPortable();
        testOrdinateur.setId(1L);
        testOrdinateur.setNom("Test Ordinateur");
        testOrdinateur.setType(TypeObjetNomade.LAPTOP);
        testOrdinateurs = Arrays.asList(testOrdinateur);
    }

    @Test
    void listOrdinateursPortables_ShouldReturnListView() {
        when(objetNomadeService.getObjetsNomadesByType(TypeObjetNomade.LAPTOP))
            .thenReturn(Arrays.asList(testOrdinateur));

        String viewName = ordinateurPortableController.listOrdinateursPortables(model);

        assertEquals("ordinateurs-portables/list", viewName);
        verify(model).addAttribute("ordinateurs", testOrdinateurs);
    }

    @Test
    void showDetails_WhenExists_ShouldReturnDetailsView() {
        when(objetNomadeService.getObjetNomadeById(1L))
            .thenReturn(Optional.of(testOrdinateur));

        String viewName = ordinateurPortableController.showDetails(1L, model, redirectAttributes);

        assertEquals("ordinateurs-portables/details", viewName);
        verify(model).addAttribute("ordinateur", testOrdinateur);
    }

    @Test
    void showDetails_WhenNotExists_ShouldRedirectToList() {
        when(objetNomadeService.getObjetNomadeById(1L))
            .thenReturn(Optional.empty());

        String viewName = ordinateurPortableController.showDetails(1L, model, redirectAttributes);

        assertEquals("redirect:/ordinateurs-portables", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Ordinateur portable non trouvé");
    }

    @Test
    void showCreateForm_ShouldReturnFormView() {
        String viewName = ordinateurPortableController.showCreateForm(model);

        assertEquals("ordinateurs-portables/form", viewName);
        verify(model).addAttribute(eq("ordinateur"), any(OrdinateurPortable.class));
    }

    @Test
    void showEditForm_WhenExists_ShouldReturnFormView() {
        when(objetNomadeService.getObjetNomadeById(1L))
            .thenReturn(Optional.of(testOrdinateur));

        String viewName = ordinateurPortableController.showEditForm(1L, model, redirectAttributes);

        assertEquals("ordinateurs-portables/form", viewName);
        verify(model).addAttribute("ordinateur", testOrdinateur);
    }

    @Test
    void showEditForm_WhenNotExists_ShouldRedirectToList() {
        when(objetNomadeService.getObjetNomadeById(1L))
            .thenReturn(Optional.empty());

        String viewName = ordinateurPortableController.showEditForm(1L, model, redirectAttributes);

        assertEquals("redirect:/ordinateurs-portables", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Ordinateur portable non trouvé");
    }

    @Test
    void saveOrdinateurPortable_WhenSuccess_ShouldRedirectToList() {
        when(objetNomadeService.createObjetNomade(any(OrdinateurPortable.class)))
            .thenReturn(testOrdinateur);

        String viewName = ordinateurPortableController.saveOrdinateurPortable(testOrdinateur, redirectAttributes);

        assertEquals("redirect:/ordinateurs-portables", viewName);
        verify(redirectAttributes).addFlashAttribute("success", "Ordinateur portable créé avec succès");
    }

    @Test
    void saveOrdinateurPortable_WhenError_ShouldRedirectToCreate() {
        when(objetNomadeService.createObjetNomade(any(OrdinateurPortable.class)))
            .thenThrow(new RuntimeException("Erreur de création"));

        String viewName = ordinateurPortableController.saveOrdinateurPortable(testOrdinateur, redirectAttributes);

        assertEquals("redirect:/ordinateurs-portables/create", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Erreur de création");
    }

    @Test
    void updateOrdinateurPortable_WhenSuccess_ShouldRedirectToList() {
        when(objetNomadeService.updateObjetNomade(eq(1L), any(OrdinateurPortable.class)))
            .thenReturn(testOrdinateur);

        String viewName = ordinateurPortableController.updateOrdinateurPortable(1L, testOrdinateur, redirectAttributes);

        assertEquals("redirect:/ordinateurs-portables", viewName);
        verify(redirectAttributes).addFlashAttribute("success", "Ordinateur portable mis à jour avec succès");
    }

    @Test
    void updateOrdinateurPortable_WhenError_ShouldRedirectToEdit() {
        when(objetNomadeService.updateObjetNomade(eq(1L), any(OrdinateurPortable.class)))
            .thenThrow(new RuntimeException("Erreur de mise à jour"));

        String viewName = ordinateurPortableController.updateOrdinateurPortable(1L, testOrdinateur, redirectAttributes);

        assertEquals("redirect:/ordinateurs-portables/1/edit", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Erreur de mise à jour");
    }

    @Test
    void deleteOrdinateurPortable_WhenSuccess_ShouldRedirectToList() {
        doNothing().when(objetNomadeService).deleteObjetNomade(1L);

        String viewName = ordinateurPortableController.deleteOrdinateurPortable(1L, redirectAttributes);

        assertEquals("redirect:/ordinateurs-portables", viewName);
        verify(redirectAttributes).addFlashAttribute("success", "Ordinateur portable supprimé avec succès");
    }

    @Test
    void deleteOrdinateurPortable_WhenError_ShouldRedirectToList() {
        doThrow(new RuntimeException("Erreur de suppression")).when(objetNomadeService).deleteObjetNomade(1L);

        String viewName = ordinateurPortableController.deleteOrdinateurPortable(1L, redirectAttributes);

        assertEquals("redirect:/ordinateurs-portables", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Erreur de suppression");
    }
} 