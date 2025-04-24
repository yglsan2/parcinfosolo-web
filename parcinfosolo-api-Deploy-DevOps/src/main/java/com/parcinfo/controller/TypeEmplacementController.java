package com.parcinfo.controller;

import com.parcinfo.model.TypeEmplacement;
import com.parcinfo.service.TypeEmplacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/types-emplacement")
public class TypeEmplacementController {

    @Autowired
    private TypeEmplacementService typeEmplacementService;

    @GetMapping
    public String listTypesEmplacement(Model model) {
        List<TypeEmplacement> typesEmplacement = typeEmplacementService.findAll();
        model.addAttribute("typesEmplacement", typesEmplacement);
        return "types-emplacement/list";
    }

    @GetMapping("/{id}")
    public String viewTypeEmplacement(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return typeEmplacementService.findById(id)
                .map(typeEmplacement -> {
                    model.addAttribute("typeEmplacement", typeEmplacement);
                    return "types-emplacement/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Type d'emplacement non trouvé");
                    return "redirect:/types-emplacement";
                });
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("typeEmplacement", new TypeEmplacement());
        return "types-emplacement/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return typeEmplacementService.findById(id)
                .map(typeEmplacement -> {
                    model.addAttribute("typeEmplacement", typeEmplacement);
                    return "types-emplacement/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Type d'emplacement non trouvé");
                    return "redirect:/types-emplacement";
                });
    }

    @PostMapping
    public String saveTypeEmplacement(@Valid @ModelAttribute TypeEmplacement typeEmplacement, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "types-emplacement/form";
        }
        
        try {
            typeEmplacementService.save(typeEmplacement);
            redirectAttributes.addFlashAttribute("success", "Type d'emplacement créé avec succès");
            return "redirect:/types-emplacement";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création du type d'emplacement: " + e.getMessage());
            return "redirect:/types-emplacement/create";
        }
    }

    @PostMapping("/{id}")
    public String updateTypeEmplacement(@PathVariable Long id, @Valid @ModelAttribute TypeEmplacement typeEmplacement, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "types-emplacement/form";
        }
        
        if (!typeEmplacementService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Type d'emplacement non trouvé");
            return "redirect:/types-emplacement";
        }
        
        try {
            typeEmplacement.setId(id);
            typeEmplacementService.save(typeEmplacement);
            redirectAttributes.addFlashAttribute("success", "Type d'emplacement mis à jour avec succès");
            return "redirect:/types-emplacement";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour du type d'emplacement: " + e.getMessage());
            return "redirect:/types-emplacement/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteTypeEmplacement(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!typeEmplacementService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Type d'emplacement non trouvé");
            return "redirect:/types-emplacement";
        }
        
        try {
            typeEmplacementService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Type d'emplacement supprimé avec succès");
            return "redirect:/types-emplacement";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression du type d'emplacement: " + e.getMessage());
            return "redirect:/types-emplacement";
        }
    }
} 