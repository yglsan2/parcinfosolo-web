package com.parcinfo.controller;

import com.parcinfo.model.Ordinateur;
import com.parcinfo.service.OrdinateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/ordinateurs")
public class OrdinateurController {

    @Autowired
    private OrdinateurService ordinateurService;

    @GetMapping
    public String listOrdinateurs(Model model) {
        List<Ordinateur> ordinateurs = ordinateurService.findAll();
        model.addAttribute("ordinateurs", ordinateurs);
        return "ordinateurs/list";
    }

    @GetMapping("/{id}")
    public String viewOrdinateur(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return ordinateurService.findById(id)
                .map(ordinateur -> {
                    model.addAttribute("ordinateur", ordinateur);
                    return "ordinateurs/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Ordinateur non trouvé");
                    return "redirect:/ordinateurs";
                });
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("ordinateur", new Ordinateur());
        return "ordinateurs/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return ordinateurService.findById(id)
                .map(ordinateur -> {
                    model.addAttribute("ordinateur", ordinateur);
                    return "ordinateurs/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Ordinateur non trouvé");
                    return "redirect:/ordinateurs";
                });
    }

    @PostMapping
    public String saveOrdinateur(@Valid @ModelAttribute Ordinateur ordinateur, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "ordinateurs/form";
        }
        
        try {
            ordinateurService.save(ordinateur);
            redirectAttributes.addFlashAttribute("success", "Ordinateur créé avec succès");
            return "redirect:/ordinateurs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création de l'ordinateur: " + e.getMessage());
            return "redirect:/ordinateurs/create";
        }
    }

    @PostMapping("/{id}")
    public String updateOrdinateur(@PathVariable Long id, @Valid @ModelAttribute Ordinateur ordinateur, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "ordinateurs/form";
        }
        
        if (!ordinateurService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Ordinateur non trouvé");
            return "redirect:/ordinateurs";
        }
        
        try {
            ordinateur.setId(id);
            ordinateurService.save(ordinateur);
            redirectAttributes.addFlashAttribute("success", "Ordinateur mis à jour avec succès");
            return "redirect:/ordinateurs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour de l'ordinateur: " + e.getMessage());
            return "redirect:/ordinateurs/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteOrdinateur(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!ordinateurService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Ordinateur non trouvé");
            return "redirect:/ordinateurs";
        }
        
        try {
            ordinateurService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Ordinateur supprimé avec succès");
            return "redirect:/ordinateurs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression de l'ordinateur: " + e.getMessage());
            return "redirect:/ordinateurs";
        }
    }
} 