package com.parcinfo.web.controller;

import com.parcinfo.web.model.ObjetNomade;
import com.parcinfo.web.service.ObjetNomadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/objets-nomades")
public class ObjetNomadeController {

    @Autowired
    private ObjetNomadeService objetNomadeService;

    @GetMapping
    public String listObjetsNomades(Model model) {
        model.addAttribute("objetsNomades", objetNomadeService.findAll());
        return "objets-nomades/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("objetNomade", new ObjetNomade());
        return "objets-nomades/form";
    }

    @GetMapping("/{id}")
    public String viewObjetNomade(@PathVariable Long id, Model model) {
        ObjetNomade objetNomade = objetNomadeService.findById(id);
        model.addAttribute("objetNomade", objetNomade);
        return "objets-nomades/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        ObjetNomade objetNomade = objetNomadeService.findById(id);
        model.addAttribute("objetNomade", objetNomade);
        return "objets-nomades/form";
    }

    @PostMapping
    public String createObjetNomade(@ModelAttribute ObjetNomade objetNomade, RedirectAttributes redirectAttributes) {
        try {
            objetNomadeService.save(objetNomade);
            redirectAttributes.addFlashAttribute("message", "Objet nomade créé avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la création de l'objet nomade");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/objets-nomades";
    }

    @PostMapping("/{id}")
    public String updateObjetNomade(@PathVariable Long id, @ModelAttribute ObjetNomade objetNomade, RedirectAttributes redirectAttributes) {
        try {
            objetNomade.setIdObjetNomade(id);
            objetNomadeService.save(objetNomade);
            redirectAttributes.addFlashAttribute("message", "Objet nomade mis à jour avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la mise à jour de l'objet nomade");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/objets-nomades";
    }

    @GetMapping("/{id}/delete")
    public String deleteObjetNomade(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            objetNomadeService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Objet nomade supprimé avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la suppression de l'objet nomade");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/objets-nomades";
    }
} 