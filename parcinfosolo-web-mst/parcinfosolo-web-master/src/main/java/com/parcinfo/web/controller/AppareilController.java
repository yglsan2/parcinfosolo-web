package com.parcinfo.web.controller;

import com.parcinfo.model.Appareil;
import com.parcinfo.service.AppareilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/appareils")
public class AppareilController {

    @Autowired
    private AppareilService appareilService;

    @GetMapping
    public String listAppareils(Model model) {
        model.addAttribute("appareils", appareilService.findAll());
        return "appareils/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("appareil", new Appareil());
        return "appareils/form";
    }

    @GetMapping("/{id}")
    public String viewAppareil(@PathVariable Long id, Model model) {
        Appareil appareil = appareilService.findById(id);
        model.addAttribute("appareil", appareil);
        return "appareils/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Appareil appareil = appareilService.findById(id);
        model.addAttribute("appareil", appareil);
        return "appareils/form";
    }

    @PostMapping
    public String createAppareil(@ModelAttribute Appareil appareil, RedirectAttributes redirectAttributes) {
        try {
            appareilService.save(appareil);
            redirectAttributes.addFlashAttribute("message", "Appareil créé avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la création de l'appareil");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/appareils";
    }

    @PostMapping("/{id}")
    public String updateAppareil(@PathVariable Long id, @ModelAttribute Appareil appareil, RedirectAttributes redirectAttributes) {
        try {
            appareil.setIdAppareil(id);
            appareilService.save(appareil);
            redirectAttributes.addFlashAttribute("message", "Appareil mis à jour avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la mise à jour de l'appareil");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/appareils";
    }

    @GetMapping("/{id}/delete")
    public String deleteAppareil(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            appareilService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Appareil supprimé avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la suppression de l'appareil");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/appareils";
    }
} 