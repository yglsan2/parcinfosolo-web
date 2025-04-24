package com.parcinfo.controller;

import com.parcinfo.model.InterventionObjetNomade;
import com.parcinfo.service.InterventionObjetNomadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/interventions-objets-nomades")
public class InterventionObjetNomadeController {

    @Autowired
    private InterventionObjetNomadeService interventionObjetNomadeService;

    @GetMapping
    public String listInterventions(Model model) {
        List<InterventionObjetNomade> interventions = interventionObjetNomadeService.findAll();
        model.addAttribute("interventions", interventions);
        return "interventions/list";
    }

    @GetMapping("/{id}")
    public String viewIntervention(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return interventionObjetNomadeService.findById(id)
                .map(intervention -> {
                    model.addAttribute("intervention", intervention);
                    return "interventions/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Intervention non trouvée");
                    return "redirect:/interventions-objets-nomades";
                });
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("intervention", new InterventionObjetNomade());
        return "interventions/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return interventionObjetNomadeService.findById(id)
                .map(intervention -> {
                    model.addAttribute("intervention", intervention);
                    return "interventions/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Intervention non trouvée");
                    return "redirect:/interventions-objets-nomades";
                });
    }

    @PostMapping
    public String saveIntervention(@Valid @ModelAttribute InterventionObjetNomade intervention, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "interventions/form";
        }
        
        try {
            interventionObjetNomadeService.save(intervention);
            redirectAttributes.addFlashAttribute("success", "Intervention créée avec succès");
            return "redirect:/interventions-objets-nomades";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création de l'intervention: " + e.getMessage());
            return "redirect:/interventions-objets-nomades/create";
        }
    }

    @PostMapping("/{id}")
    public String updateIntervention(@PathVariable Long id, @Valid @ModelAttribute InterventionObjetNomade intervention, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "interventions/form";
        }
        
        if (!interventionObjetNomadeService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Intervention non trouvée");
            return "redirect:/interventions-objets-nomades";
        }
        
        try {
            intervention.setId(id);
            interventionObjetNomadeService.save(intervention);
            redirectAttributes.addFlashAttribute("success", "Intervention mise à jour avec succès");
            return "redirect:/interventions-objets-nomades";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour de l'intervention: " + e.getMessage());
            return "redirect:/interventions-objets-nomades/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteIntervention(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!interventionObjetNomadeService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Intervention non trouvée");
            return "redirect:/interventions-objets-nomades";
        }
        
        try {
            interventionObjetNomadeService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Intervention supprimée avec succès");
            return "redirect:/interventions-objets-nomades";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression de l'intervention: " + e.getMessage());
            return "redirect:/interventions-objets-nomades";
        }
    }
} 