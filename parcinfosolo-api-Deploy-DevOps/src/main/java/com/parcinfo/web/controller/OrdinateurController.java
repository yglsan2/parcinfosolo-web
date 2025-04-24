package com.parcinfo.web.controller;

import com.parcinfo.model.Ordinateur;
import com.parcinfo.web.service.OrdinateurService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ordinateurs")
public class OrdinateurController {

    private final OrdinateurService ordinateurService;

    public OrdinateurController(OrdinateurService ordinateurService) {
        this.ordinateurService = ordinateurService;
    }

    @GetMapping
    public String listeOrdinateurs(Model model) {
        model.addAttribute("ordinateurs", ordinateurService.findAll());
        return "ordinateurs/liste";
    }

    @GetMapping("/{id}")
    public String detailOrdinateur(@PathVariable Long id, Model model) {
        model.addAttribute("ordinateur", ordinateurService.findById(id));
        return "ordinateurs/detail";
    }

    @GetMapping("/nouveau")
    public String nouveauOrdinateur(Model model) {
        model.addAttribute("ordinateur", new Ordinateur());
        return "ordinateurs/form";
    }

    @PostMapping
    public String sauvegarderOrdinateur(@Valid Ordinateur ordinateur, 
                                      BindingResult result, 
                                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "ordinateurs/form";
        }
        
        ordinateurService.save(ordinateur);
        redirectAttributes.addFlashAttribute("message", "L'ordinateur a été sauvegardé avec succès.");
        return "redirect:/ordinateurs";
    }

    @GetMapping("/{id}/modifier")
    public String modifierOrdinateur(@PathVariable Long id, Model model) {
        model.addAttribute("ordinateur", ordinateurService.findById(id));
        return "ordinateurs/form";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimerOrdinateur(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ordinateurService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "L'ordinateur a été supprimé avec succès.");
        return "redirect:/ordinateurs";
    }
} 