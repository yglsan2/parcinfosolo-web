package com.parcinfo.controller;

import com.parcinfo.model.Personne;
import com.parcinfo.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/personnes")
public class PersonneController {

    @Autowired
    private PersonneService personneService;

    @GetMapping
    public String listPersonnes(Model model) {
        List<Personne> personnes = personneService.findAll();
        model.addAttribute("personnes", personnes);
        return "personnes/list";
    }

    @GetMapping("/{id}")
    public String viewPersonne(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return personneService.findById(id)
                .map(personne -> {
                    model.addAttribute("personne", personne);
                    return "personnes/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Personne non trouvée");
                    return "redirect:/personnes";
                });
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("personne", new Personne());
        return "personnes/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return personneService.findById(id)
                .map(personne -> {
                    model.addAttribute("personne", personne);
                    return "personnes/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Personne non trouvée");
                    return "redirect:/personnes";
                });
    }

    @PostMapping
    public String savePersonne(@Valid @ModelAttribute Personne personne, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "personnes/form";
        }
        
        try {
            personneService.save(personne);
            redirectAttributes.addFlashAttribute("success", "Personne créée avec succès");
            return "redirect:/personnes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création de la personne: " + e.getMessage());
            return "redirect:/personnes/create";
        }
    }

    @PostMapping("/{id}")
    public String updatePersonne(@PathVariable Long id, @Valid @ModelAttribute Personne personne, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "personnes/form";
        }
        
        if (!personneService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Personne non trouvée");
            return "redirect:/personnes";
        }
        
        try {
            personne.setId(id);
            personneService.save(personne);
            redirectAttributes.addFlashAttribute("success", "Personne mise à jour avec succès");
            return "redirect:/personnes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour de la personne: " + e.getMessage());
            return "redirect:/personnes/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deletePersonne(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!personneService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Personne non trouvée");
            return "redirect:/personnes";
        }
        
        try {
            personneService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Personne supprimée avec succès");
            return "redirect:/personnes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression de la personne: " + e.getMessage());
            return "redirect:/personnes";
        }
    }
} 