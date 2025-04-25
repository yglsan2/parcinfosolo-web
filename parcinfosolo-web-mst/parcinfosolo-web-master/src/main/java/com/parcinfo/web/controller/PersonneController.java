package com.parcinfo.web.controller;

import com.parcinfo.web.model.PersonneWeb;
import com.parcinfo.web.service.PersonneService;
import com.parcinfo.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/personnes")
public class PersonneController {
    private static final Logger logger = LoggerFactory.getLogger(PersonneController.class);
    private final PersonneService personneService;

    public PersonneController(PersonneService personneService) {
        this.personneService = personneService;
    }

    @GetMapping
    public String listPersonnes(Model model) {
        logger.debug("Affichage de la liste des personnes");
        List<PersonneWeb> personnes = personneService.findAll();
        model.addAttribute("personnes", personnes);
        return "personnes/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        logger.debug("Affichage du formulaire de création");
        model.addAttribute("personne", new PersonneWeb(null));
        return "personnes/form";
    }

    @GetMapping("/{id}")
    public String viewPersonne(@PathVariable Long id, Model model) {
        logger.debug("Affichage des détails de la personne {}", id);
        PersonneWeb personne = personneService.findById(id);
        model.addAttribute("personne", personne);
        return "personnes/show";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        logger.debug("Affichage du formulaire d'édition pour la personne {}", id);
        PersonneWeb personne = personneService.findById(id);
        model.addAttribute("personne", personne);
        return "personnes/form";
    }

    @PostMapping
    public String createPersonne(@ModelAttribute PersonneWeb personne, RedirectAttributes redirectAttributes) {
        logger.debug("Création d'une nouvelle personne: {}", personne);
        try {
            personneService.save(personne);
            redirectAttributes.addFlashAttribute("message", "Personne créée avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (BusinessException e) {
            logger.error("Erreur métier lors de la création de la personne: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        } catch (Exception e) {
            logger.error("Erreur inattendue lors de la création de la personne", e);
            redirectAttributes.addFlashAttribute("message", "Une erreur inattendue s'est produite");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/personnes";
    }

    @PostMapping("/{id}")
    public String updatePersonne(@PathVariable Long id, @ModelAttribute PersonneWeb personne, RedirectAttributes redirectAttributes) {
        logger.debug("Mise à jour de la personne {}: {}", id, personne);
        try {
            personne.setId(id);
            personneService.save(personne);
            redirectAttributes.addFlashAttribute("message", "Personne mise à jour avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (BusinessException e) {
            logger.error("Erreur métier lors de la mise à jour de la personne: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        } catch (Exception e) {
            logger.error("Erreur inattendue lors de la mise à jour de la personne", e);
            redirectAttributes.addFlashAttribute("message", "Une erreur inattendue s'est produite");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/personnes";
    }

    @GetMapping("/{id}/delete")
    public String deletePersonne(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        logger.debug("Suppression de la personne {}", id);
        try {
            personneService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Personne supprimée avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (BusinessException e) {
            logger.error("Erreur métier lors de la suppression de la personne: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        } catch (Exception e) {
            logger.error("Erreur inattendue lors de la suppression de la personne", e);
            redirectAttributes.addFlashAttribute("message", "Une erreur inattendue s'est produite");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/personnes";
    }
} 