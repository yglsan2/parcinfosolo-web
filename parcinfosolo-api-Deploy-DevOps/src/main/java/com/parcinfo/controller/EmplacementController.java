package com.parcinfo.controller;

import com.parcinfo.model.Emplacement;
import com.parcinfo.service.EmplacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/emplacements")
public class EmplacementController {

    @Autowired
    private EmplacementService emplacementService;

    @GetMapping
    public String listEmplacements(Model model) {
        List<Emplacement> emplacements = emplacementService.findAll();
        model.addAttribute("emplacements", emplacements);
        return "emplacements/list";
    }

    @GetMapping("/{id}")
    public String viewEmplacement(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return emplacementService.findById(id)
                .map(emplacement -> {
                    model.addAttribute("emplacement", emplacement);
                    return "emplacements/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Emplacement non trouvé");
                    return "redirect:/emplacements";
                });
    }

    @GetMapping("/parc/{parcId}")
    public String listEmplacementsByParc(@PathVariable Long parcId, Model model) {
        List<Emplacement> emplacements = emplacementService.findByParcId(parcId);
        model.addAttribute("emplacements", emplacements);
        model.addAttribute("parcId", parcId);
        return "emplacements/list";
    }

    @GetMapping("/type/{typeEmplacementId}")
    public String listEmplacementsByType(@PathVariable Long typeEmplacementId, Model model) {
        List<Emplacement> emplacements = emplacementService.findByTypeEmplacementId(typeEmplacementId);
        model.addAttribute("emplacements", emplacements);
        model.addAttribute("typeEmplacementId", typeEmplacementId);
        return "emplacements/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("emplacement", new Emplacement());
        return "emplacements/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return emplacementService.findById(id)
                .map(emplacement -> {
                    model.addAttribute("emplacement", emplacement);
                    return "emplacements/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Emplacement non trouvé");
                    return "redirect:/emplacements";
                });
    }

    @PostMapping
    public String saveEmplacement(@Valid @ModelAttribute Emplacement emplacement, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "emplacements/form";
        }
        
        try {
            emplacementService.save(emplacement);
            redirectAttributes.addFlashAttribute("success", "Emplacement créé avec succès");
            return "redirect:/emplacements";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création de l'emplacement: " + e.getMessage());
            return "redirect:/emplacements/create";
        }
    }

    @PostMapping("/{id}")
    public String updateEmplacement(@PathVariable Long id, @Valid @ModelAttribute Emplacement emplacement, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "emplacements/form";
        }
        
        if (!emplacementService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Emplacement non trouvé");
            return "redirect:/emplacements";
        }
        
        try {
            emplacement.setId(id);
            emplacementService.save(emplacement);
            redirectAttributes.addFlashAttribute("success", "Emplacement mis à jour avec succès");
            return "redirect:/emplacements";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour de l'emplacement: " + e.getMessage());
            return "redirect:/emplacements/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteEmplacement(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!emplacementService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Emplacement non trouvé");
            return "redirect:/emplacements";
        }
        
        try {
            emplacementService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Emplacement supprimé avec succès");
            return "redirect:/emplacements";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression de l'emplacement: " + e.getMessage());
            return "redirect:/emplacements";
        }
    }
} 