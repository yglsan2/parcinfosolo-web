package com.parcinfo.web.controller;

import com.parcinfo.web.model.Personne;
import com.parcinfo.web.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/personnes")
public class PersonneController {

    @Autowired
    private PersonneService personneService;

    @GetMapping
    public String listPersonnes(Model model) {
        model.addAttribute("personnes", personneService.findAll());
        return "personnes/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("personne", new Personne());
        return "personnes/form";
    }

    @GetMapping("/{id}")
    public String viewPersonne(@PathVariable Long id, Model model) {
        Personne personne = personneService.findById(id);
        model.addAttribute("personne", personne);
        return "personnes/show";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Personne personne = personneService.findById(id);
        model.addAttribute("personne", personne);
        return "personnes/form";
    }

    @PostMapping
    public String createPersonne(@ModelAttribute Personne personne, RedirectAttributes redirectAttributes) {
        try {
            personneService.save(personne);
            redirectAttributes.addFlashAttribute("message", "Personne créée avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la création de la personne");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/personnes";
    }

    @PostMapping("/{id}")
    public String updatePersonne(@PathVariable Long id, @ModelAttribute Personne personne, RedirectAttributes redirectAttributes) {
        try {
            personne.setIdPersonne(id);
            personneService.save(personne);
            redirectAttributes.addFlashAttribute("message", "Personne mise à jour avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la mise à jour de la personne");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/personnes";
    }

    @GetMapping("/{id}/delete")
    public String deletePersonne(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            personneService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Personne supprimée avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la suppression de la personne");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/personnes";
    }
} 