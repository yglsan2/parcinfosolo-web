package com.parcinfo.web.controller;

import com.parcinfo.model.Ordinateur;
import com.parcinfo.service.OrdinateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ordinateurs")
public class OrdinateurController {

    @Autowired
    private OrdinateurService ordinateurService;

    @GetMapping
    public String listOrdinateurs(Model model) {
        model.addAttribute("ordinateurs", ordinateurService.findAll());
        return "ordinateurs/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ordinateur", new Ordinateur());
        return "ordinateurs/form";
    }

    @GetMapping("/{id}")
    public String viewOrdinateur(@PathVariable Long id, Model model) {
        Ordinateur ordinateur = ordinateurService.findById(id);
        model.addAttribute("ordinateur", ordinateur);
        return "ordinateurs/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Ordinateur ordinateur = ordinateurService.findById(id);
        model.addAttribute("ordinateur", ordinateur);
        return "ordinateurs/form";
    }

    @PostMapping
    public String createOrdinateur(@ModelAttribute Ordinateur ordinateur, RedirectAttributes redirectAttributes) {
        try {
            ordinateurService.save(ordinateur);
            redirectAttributes.addFlashAttribute("message", "Ordinateur créé avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la création de l'ordinateur");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/ordinateurs";
    }

    @PostMapping("/{id}")
    public String updateOrdinateur(@PathVariable Long id, @ModelAttribute Ordinateur ordinateur, RedirectAttributes redirectAttributes) {
        try {
            ordinateur.setIdAppareil(id);
            ordinateurService.save(ordinateur);
            redirectAttributes.addFlashAttribute("message", "Ordinateur mis à jour avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la mise à jour de l'ordinateur");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/ordinateurs";
    }

    @GetMapping("/{id}/delete")
    public String deleteOrdinateur(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            ordinateurService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Ordinateur supprimé avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la suppression de l'ordinateur");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/ordinateurs";
    }
} 