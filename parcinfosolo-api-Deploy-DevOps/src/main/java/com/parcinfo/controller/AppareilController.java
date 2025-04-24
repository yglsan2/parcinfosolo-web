package com.parcinfo.controller;

import com.parcinfo.model.Appareil;
import com.parcinfo.model.Ordinateur;
import com.parcinfo.model.ObjetNomade;
import com.parcinfo.service.AppareilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/appareils")
public class AppareilController {
    
    @Autowired
    private AppareilService appareilService;
    
    @GetMapping
    public String listAppareils(Model model) {
        List<Appareil> appareils = appareilService.findAll();
        model.addAttribute("appareils", appareils);
        return "appareils/list";
    }
    
    @GetMapping("/{id}")
    public String viewAppareil(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return appareilService.findById(id)
            .map(appareil -> {
                model.addAttribute("appareil", appareil);
                return "appareils/view";
            })
            .orElseGet(() -> {
                redirectAttributes.addFlashAttribute("error", "Appareil non trouvé");
                return "redirect:/appareils";
            });
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("appareil", new Ordinateur());
        return "appareils/form";
    }
    
    @PostMapping
    public String createAppareil(@ModelAttribute Ordinateur appareil, RedirectAttributes redirectAttributes) {
        try {
            appareilService.save(appareil);
            redirectAttributes.addFlashAttribute("success", "Appareil créé avec succès");
            return "redirect:/appareils";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création de l'appareil: " + e.getMessage());
            return "redirect:/appareils/create";
        }
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return appareilService.findById(id)
            .map(appareil -> {
                model.addAttribute("appareil", appareil);
                return "appareils/form";
            })
            .orElseGet(() -> {
                redirectAttributes.addFlashAttribute("error", "Appareil non trouvé");
                return "redirect:/appareils";
            });
    }
    
    @PostMapping("/{id}")
    public String updateAppareil(@PathVariable Long id, @ModelAttribute Ordinateur appareil, RedirectAttributes redirectAttributes) {
        if (!appareilService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Appareil non trouvé");
            return "redirect:/appareils";
        }
        
        try {
            appareil.setId(id);
            appareilService.save(appareil);
            redirectAttributes.addFlashAttribute("success", "Appareil mis à jour avec succès");
            return "redirect:/appareils";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour de l'appareil: " + e.getMessage());
            return "redirect:/appareils/" + id + "/edit";
        }
    }
    
    @PostMapping("/{id}/delete")
    public String deleteAppareil(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!appareilService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Appareil non trouvé");
            return "redirect:/appareils";
        }
        
        try {
            appareilService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Appareil supprimé avec succès");
            return "redirect:/appareils";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression de l'appareil: " + e.getMessage());
            return "redirect:/appareils";
        }
    }
} 