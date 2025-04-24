package com.parcinfo.web.controller;

import com.parcinfo.web.model.Peripherique;
import com.parcinfo.web.service.PeripheriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/peripheriques")
public class PeripheriqueController {

    @Autowired
    private PeripheriqueService peripheriqueService;

    @GetMapping
    public String listPeripheriques(Model model) {
        List<Peripherique> peripheriques = peripheriqueService.findAll();
        model.addAttribute("peripheriques", peripheriques);
        return "peripheriques/list";
    }

    @GetMapping("/{id}")
    public String viewPeripherique(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return peripheriqueService.findById(id)
                .map(peripherique -> {
                    model.addAttribute("peripherique", peripherique);
                    return "peripheriques/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Périphérique non trouvé");
                    return "redirect:/peripheriques";
                });
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("peripherique", new Peripherique());
        return "peripheriques/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return peripheriqueService.findById(id)
                .map(peripherique -> {
                    model.addAttribute("peripherique", peripherique);
                    return "peripheriques/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Périphérique non trouvé");
                    return "redirect:/peripheriques";
                });
    }

    @PostMapping
    public String savePeripherique(@Valid @ModelAttribute Peripherique peripherique, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "peripheriques/form";
        }
        
        try {
            peripheriqueService.save(peripherique);
            redirectAttributes.addFlashAttribute("success", "Périphérique créé avec succès");
            return "redirect:/peripheriques";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création du périphérique: " + e.getMessage());
            return "redirect:/peripheriques/create";
        }
    }

    @PostMapping("/{id}")
    public String updatePeripherique(@PathVariable Long id, @Valid @ModelAttribute Peripherique peripherique, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "peripheriques/form";
        }
        
        if (!peripheriqueService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Périphérique non trouvé");
            return "redirect:/peripheriques";
        }
        
        try {
            peripherique.setId(id);
            peripheriqueService.save(peripherique);
            redirectAttributes.addFlashAttribute("success", "Périphérique mis à jour avec succès");
            return "redirect:/peripheriques";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour du périphérique: " + e.getMessage());
            return "redirect:/peripheriques/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deletePeripherique(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!peripheriqueService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Périphérique non trouvé");
            return "redirect:/peripheriques";
        }
        
        try {
            peripheriqueService.deletePeripherique(id);
            redirectAttributes.addFlashAttribute("success", "Périphérique supprimé avec succès");
            return "redirect:/peripheriques";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression du périphérique: " + e.getMessage());
            return "redirect:/peripheriques";
        }
    }
} 