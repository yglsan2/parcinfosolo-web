package com.parcinfo.web.controller;

import com.parcinfo.web.model.Ordinateur;
import com.parcinfo.web.service.OrdinateurService;
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
        try {
            Ordinateur ordinateur = ordinateurService.findById(id);
            model.addAttribute("ordinateur", ordinateur);
            return "ordinateurs/view";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Ordinateur non trouvé");
            return "redirect:/ordinateurs";
        }
    }

    @GetMapping("/nouveau")
    public String nouveauOrdinateur(Model model) {
        model.addAttribute("ordinateur", new Ordinateur());
        return "ordinateurs/form";
    }

    @GetMapping("/{id}/modifier")
    public String modifierOrdinateur(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Ordinateur ordinateur = ordinateurService.findById(id);
            model.addAttribute("ordinateur", ordinateur);
            return "ordinateurs/form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Ordinateur non trouvé");
            return "redirect:/ordinateurs";
        }
    }

    @PostMapping
    public String sauvegarderOrdinateur(@Valid @ModelAttribute Ordinateur ordinateur, 
                                BindingResult bindingResult, 
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "ordinateurs/form";
        }
        
        try {
            ordinateurService.save(ordinateur);
            redirectAttributes.addFlashAttribute("success", "Ordinateur créé avec succès");
            return "redirect:/ordinateurs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création de l'ordinateur: " + e.getMessage());
            return "redirect:/ordinateurs/nouveau";
        }
    }

    @PostMapping("/{id}")
    public String updateOrdinateur(@PathVariable Long id, 
                                 @Valid @ModelAttribute Ordinateur ordinateur, 
                                 BindingResult bindingResult, 
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "ordinateurs/form";
        }
        
        try {
            ordinateur.setId(id);
            ordinateurService.save(ordinateur);
            redirectAttributes.addFlashAttribute("success", "Ordinateur mis à jour avec succès");
            return "redirect:/ordinateurs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour de l'ordinateur: " + e.getMessage());
            return "redirect:/ordinateurs/" + id + "/modifier";
        }
    }

    @PostMapping("/{id}/supprimer")
    public String supprimerOrdinateur(@PathVariable Long id, RedirectAttributes redirectAttributes) {
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