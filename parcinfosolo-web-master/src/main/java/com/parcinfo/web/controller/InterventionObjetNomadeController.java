package com.parcinfo.web.controller;

import com.parcinfo.web.model.InterventionObjetNomade;
import com.parcinfo.web.service.InterventionObjetNomadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/interventions-objets-nomades")
public class InterventionObjetNomadeController {

    @Autowired
    private InterventionObjetNomadeService interventionService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("interventions", interventionService.findAll());
        return "interventions-objets-nomades/list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("intervention", new InterventionObjetNomade());
        return "interventions-objets-nomades/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        interventionService.findById(id).ifPresent(intervention -> 
            model.addAttribute("intervention", intervention));
        return "interventions-objets-nomades/form";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        interventionService.findById(id).ifPresent(intervention -> 
            model.addAttribute("intervention", intervention));
        return "interventions-objets-nomades/view";
    }

    @PostMapping
    public String save(@ModelAttribute InterventionObjetNomade intervention, RedirectAttributes redirectAttributes) {
        interventionService.save(intervention);
        redirectAttributes.addFlashAttribute("success", "L'intervention a été enregistrée avec succès.");
        return "redirect:/interventions-objets-nomades";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute InterventionObjetNomade intervention, 
                        RedirectAttributes redirectAttributes) {
        if (interventionService.update(id, intervention) != null) {
            redirectAttributes.addFlashAttribute("success", "L'intervention a été mise à jour avec succès.");
        } else {
            redirectAttributes.addFlashAttribute("error", "L'intervention n'a pas été trouvée.");
        }
        return "redirect:/interventions-objets-nomades";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        interventionService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "L'intervention a été supprimée avec succès.");
        return "redirect:/interventions-objets-nomades";
    }
} 