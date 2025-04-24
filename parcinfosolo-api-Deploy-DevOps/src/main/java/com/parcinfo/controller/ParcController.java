package com.parcinfo.controller;

import com.parcinfo.model.Parc;
import com.parcinfo.service.ParcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/parcs")
public class ParcController {

    @Autowired
    private ParcService parcService;

    @GetMapping
    public String listParcs(Model model) {
        List<Parc> parcs = parcService.findAll();
        model.addAttribute("parcs", parcs);
        return "parcs/list";
    }

    @GetMapping("/{id}")
    public String viewParc(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return parcService.findById(id)
                .map(parc -> {
                    model.addAttribute("parc", parc);
                    return "parcs/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Parc non trouvé");
                    return "redirect:/parcs";
                });
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("parc", new Parc());
        return "parcs/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return parcService.findById(id)
                .map(parc -> {
                    model.addAttribute("parc", parc);
                    return "parcs/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Parc non trouvé");
                    return "redirect:/parcs";
                });
    }

    @PostMapping
    public String saveParc(@Valid @ModelAttribute Parc parc, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "parcs/form";
        }
        
        try {
            parcService.save(parc);
            redirectAttributes.addFlashAttribute("success", "Parc créé avec succès");
            return "redirect:/parcs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création du parc: " + e.getMessage());
            return "redirect:/parcs/create";
        }
    }

    @PostMapping("/{id}")
    public String updateParc(@PathVariable Long id, @Valid @ModelAttribute Parc parc, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "parcs/form";
        }
        
        if (!parcService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Parc non trouvé");
            return "redirect:/parcs";
        }
        
        try {
            parc.setId(id);
            parcService.save(parc);
            redirectAttributes.addFlashAttribute("success", "Parc mis à jour avec succès");
            return "redirect:/parcs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour du parc: " + e.getMessage());
            return "redirect:/parcs/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteParc(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!parcService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Parc non trouvé");
            return "redirect:/parcs";
        }
        
        try {
            parcService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Parc supprimé avec succès");
            return "redirect:/parcs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression du parc: " + e.getMessage());
            return "redirect:/parcs";
        }
    }
} 