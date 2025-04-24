package com.parcinfo.controller;

import com.parcinfo.model.Personne;
import com.parcinfo.model.Role;
import com.parcinfo.model.RoleType;
import com.parcinfo.web.service.PersonneService;
import com.parcinfo.web.controller.PersonneController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonneControllerTest {

    @Mock
    private PersonneService personneService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private PersonneController personneController;

    private Personne personne;

    @BeforeEach
    void setUp() {
        personne = new Personne();
        personne.setFirstname("John");
        personne.setLastname("Doe");
        personne.setEmail("john.doe@example.com");
        personne.setPassword("password123");
        personne.setRole(new Role(RoleType.USER));
    }

    @Test
    @DisplayName("Devrait afficher la liste des personnes")
    void shouldListPersonnes() {
        // Arrange
        List<Personne> personnes = Arrays.asList(personne);
        when(personneService.findAll()).thenReturn(personnes);

        // Act
        String viewName = personneController.listPersonnes(model);

        // Assert
        assertEquals("personnes/list", viewName);
        verify(model).addAttribute("personnes", personnes);
    }

    @Test
    @DisplayName("Devrait afficher les détails d'une personne")
    void shouldViewPersonne() {
        // Arrange
        Long id = 1L;
        when(personneService.findById(id)).thenReturn(Optional.of(personne));

        // Act
        String viewName = personneController.viewPersonne(id, model, redirectAttributes);

        // Assert
        assertEquals("personnes/view", viewName);
        verify(model).addAttribute("personne", personne);
    }

    @Test
    @DisplayName("Devrait rediriger si la personne n'est pas trouvée")
    void shouldRedirectWhenPersonneNotFound() {
        // Arrange
        Long id = 1L;
        when(personneService.findById(id)).thenReturn(Optional.empty());

        // Act
        String viewName = personneController.viewPersonne(id, model, redirectAttributes);

        // Assert
        assertEquals("redirect:/personnes", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Personne non trouvée");
    }

    @Test
    @DisplayName("Devrait afficher le formulaire de création")
    void shouldShowCreateForm() {
        // Act
        String viewName = personneController.showCreateForm(model);

        // Assert
        assertEquals("personnes/form", viewName);
        verify(model).addAttribute(eq("personne"), any(Personne.class));
    }

    @Test
    @DisplayName("Devrait afficher le formulaire de modification")
    void shouldShowEditForm() {
        // Arrange
        Long id = 1L;
        when(personneService.findById(id)).thenReturn(Optional.of(personne));

        // Act
        String viewName = personneController.showEditForm(id, model, redirectAttributes);

        // Assert
        assertEquals("personnes/form", viewName);
        verify(model).addAttribute("personne", personne);
    }

    @Test
    @DisplayName("Devrait rediriger si la personne à modifier n'est pas trouvée")
    void shouldRedirectWhenEditingNonExistentPersonne() {
        // Arrange
        Long id = 1L;
        when(personneService.findById(id)).thenReturn(Optional.empty());

        // Act
        String viewName = personneController.showEditForm(id, model, redirectAttributes);

        // Assert
        assertEquals("redirect:/personnes", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Personne non trouvée");
    }

    @Test
    @DisplayName("Devrait sauvegarder une nouvelle personne")
    void shouldSavePersonne() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(personneService.save(any(Personne.class))).thenReturn(personne);

        // Act
        String viewName = personneController.savePersonne(personne, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/personnes", viewName);
        verify(redirectAttributes).addFlashAttribute("success", "Personne créée avec succès");
    }

    @Test
    @DisplayName("Devrait retourner au formulaire si la validation échoue")
    void shouldReturnToFormWhenValidationFails() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = personneController.savePersonne(personne, bindingResult, redirectAttributes);

        // Assert
        assertEquals("personnes/form", viewName);
    }

    @Test
    @DisplayName("Devrait mettre à jour une personne existante")
    void shouldUpdatePersonne() {
        // Arrange
        Long id = 1L;
        when(bindingResult.hasErrors()).thenReturn(false);
        when(personneService.findById(id)).thenReturn(Optional.of(personne));
        when(personneService.save(any(Personne.class))).thenReturn(personne);

        // Act
        String viewName = personneController.updatePersonne(id, personne, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/personnes", viewName);
        verify(redirectAttributes).addFlashAttribute("success", "Personne mise à jour avec succès");
    }

    @Test
    @DisplayName("Devrait supprimer une personne")
    void shouldDeletePersonne() {
        // Arrange
        Long id = 1L;
        when(personneService.findById(id)).thenReturn(Optional.of(personne));

        // Act
        String viewName = personneController.deletePersonne(id, redirectAttributes);

        // Assert
        assertEquals("redirect:/personnes", viewName);
        verify(redirectAttributes).addFlashAttribute("success", "Personne supprimée avec succès");
    }

    @Test
    @DisplayName("Devrait rediriger si la personne à supprimer n'est pas trouvée")
    void shouldRedirectWhenDeletingNonExistentPersonne() {
        // Arrange
        Long id = 1L;
        when(personneService.findById(id)).thenReturn(Optional.empty());

        // Act
        String viewName = personneController.deletePersonne(id, redirectAttributes);

        // Assert
        assertEquals("redirect:/personnes", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Personne non trouvée");
    }
} 