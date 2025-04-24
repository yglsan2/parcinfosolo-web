package com.parcinfo.controller;

import com.parcinfo.model.Affectation;
import com.parcinfo.service.AffectationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/affectations")
public class AffectationController {
    
    @Autowired
    private AffectationService affectationService;
    
    @GetMapping
    public String listAffectations(Model model) {
        List<Affectation> affectations = affectationService.findAll();
        model.addAttribute("affectations", affectations);
        return "affectations/list";
    }
    
    @GetMapping("/{id}")
    public String viewAffectation(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return affectationService.findById(id)
            .map(affectation -> {
                model.addAttribute("affectation", affectation);
                return "affectations/view";
            })
            .orElseGet(() -> {
                redirectAttributes.addFlashAttribute("error", "Affectation non trouvée");
                return "redirect:/affectations";
            });
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("affectation", new Affectation());
        return "affectations/form";
    }
    
    @PostMapping
    public String createAffectation(@ModelAttribute Affectation affectation, RedirectAttributes redirectAttributes) {
        try {
            affectationService.save(affectation);
            redirectAttributes.addFlashAttribute("success", "Affectation créée avec succès");
            return "redirect:/affectations";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création de l'affectation: " + e.getMessage());
            return "redirect:/affectations/create";
        }
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return affectationService.findById(id)
            .map(affectation -> {
                model.addAttribute("affectation", affectation);
                return "affectations/form";
            })
            .orElseGet(() -> {
                redirectAttributes.addFlashAttribute("error", "Affectation non trouvée");
                return "redirect:/affectations";
            });
    }
    
    @PostMapping("/{id}")
    public String updateAffectation(@PathVariable Long id, @ModelAttribute Affectation affectation, RedirectAttributes redirectAttributes) {
        if (!affectationService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Affectation non trouvée");
            return "redirect:/affectations";
        }
        
        try {
            affectation.setId(id);
            affectationService.save(affectation);
            redirectAttributes.addFlashAttribute("success", "Affectation mise à jour avec succès");
            return "redirect:/affectations";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour de l'affectation: " + e.getMessage());
            return "redirect:/affectations/" + id + "/edit";
        }
    }
    
    @PostMapping("/{id}/delete")
    public String deleteAffectation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!affectationService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Affectation non trouvée");
            return "redirect:/affectations";
        }
        
        try {
            affectationService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Affectation supprimée avec succès");
            return "redirect:/affectations";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression de l'affectation: " + e.getMessage());
            return "redirect:/affectations";
        }
    }
} 