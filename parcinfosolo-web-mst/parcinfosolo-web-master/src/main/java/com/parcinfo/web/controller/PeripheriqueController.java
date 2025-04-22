package com.parcinfo.web.controller;

import com.parcinfo.web.model.Peripherique;
import com.parcinfo.web.model.Intervention;
import com.parcinfo.web.service.PeripheriqueService;
import com.parcinfo.web.service.InterventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/peripheriques")
public class PeripheriqueController {

    @Autowired
    private PeripheriqueService peripheriqueService;

    @Autowired
    private InterventionService interventionService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("peripheriques", peripheriqueService.findAll());
        return "peripheriques/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("peripherique", new Peripherique());
        return "peripheriques/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Peripherique peripherique = peripheriqueService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Périphérique invalide:" + id));
        model.addAttribute("peripherique", peripherique);
        return "peripheriques/form";
    }

    @PostMapping("/save")
    public String save(@Valid Peripherique peripherique, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "peripheriques/form";
        }

        if (peripherique.getId() == null) {
            peripherique.setDateAjout(LocalDateTime.now());
        }

        peripheriqueService.save(peripherique);
        redirectAttributes.addFlashAttribute("message", "Le périphérique a été enregistré avec succès.");
        return "redirect:/peripheriques";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Peripherique peripherique = peripheriqueService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Périphérique invalide:" + id));
        model.addAttribute("peripherique", peripherique);
        return "peripheriques/view";
    }

    @PostMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Peripherique peripherique = peripheriqueService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Périphérique invalide:" + id));
        peripheriqueService.delete(peripherique);
        redirectAttributes.addFlashAttribute("message", "Le périphérique a été supprimé avec succès.");
        return "redirect:/peripheriques";
    }

    // Gestion des interventions
    @GetMapping("/{id}/interventions")
    public String listInterventions(@PathVariable Long id, Model model) {
        Peripherique peripherique = peripheriqueService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Périphérique invalide:" + id));
        model.addAttribute("peripherique", peripherique);
        model.addAttribute("interventions", interventionService.findByPeripheriqueId(id));
        return "peripheriques/interventions";
    }

    @PostMapping("/{id}/interventions")
    public String addIntervention(@PathVariable Long id, @Valid Intervention intervention, 
                                BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "peripheriques/interventions";
        }

        Peripherique peripherique = peripheriqueService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Périphérique invalide:" + id));
        
        intervention.setPeripherique(peripherique);
        intervention.setDate(LocalDateTime.now());
        
        interventionService.save(intervention);
        
        // Mise à jour de la dernière maintenance si c'est une maintenance
        if (intervention.getType().equals("MAINTENANCE")) {
            peripherique.setDerniereMaintenance(intervention.getDate());
            peripheriqueService.save(peripherique);
        }
        
        redirectAttributes.addFlashAttribute("message", "L'intervention a été enregistrée avec succès.");
        return "redirect:/peripheriques/" + id + "/interventions";
    }

    @GetMapping("/{id}/interventions/{interventionId}/edit")
    public String showEditInterventionForm(@PathVariable Long id, @PathVariable Long interventionId, Model model) {
        Peripherique peripherique = peripheriqueService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Périphérique invalide:" + id));
        Intervention intervention = interventionService.findById(interventionId)
            .orElseThrow(() -> new IllegalArgumentException("Intervention invalide:" + interventionId));
        
        model.addAttribute("peripherique", peripherique);
        model.addAttribute("intervention", intervention);
        return "peripheriques/intervention-edit";
    }

    @PostMapping("/{id}/interventions/{interventionId}")
    public String updateIntervention(@PathVariable Long id, @PathVariable Long interventionId,
                                   @Valid Intervention intervention, BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "peripheriques/intervention-edit";
        }

        Intervention existingIntervention = interventionService.findById(interventionId)
            .orElseThrow(() -> new IllegalArgumentException("Intervention invalide:" + interventionId));
        
        existingIntervention.setType(intervention.getType());
        existingIntervention.setDate(intervention.getDate());
        existingIntervention.setDescription(intervention.getDescription());
        existingIntervention.setTechnicien(intervention.getTechnicien());
        existingIntervention.setDuree(intervention.getDuree());
        existingIntervention.setResultat(intervention.getResultat());
        
        interventionService.save(existingIntervention);
        
        redirectAttributes.addFlashAttribute("message", "L'intervention a été modifiée avec succès.");
        return "redirect:/peripheriques/" + id + "/interventions";
    }

    @PostMapping("/{id}/interventions/{interventionId}/delete")
    public String deleteIntervention(@PathVariable Long id, @PathVariable Long interventionId,
                                   RedirectAttributes redirectAttributes) {
        Intervention intervention = interventionService.findById(interventionId)
            .orElseThrow(() -> new IllegalArgumentException("Intervention invalide:" + interventionId));
        
        interventionService.delete(intervention);
        
        redirectAttributes.addFlashAttribute("message", "L'intervention a été supprimée avec succès.");
        return "redirect:/peripheriques/" + id + "/interventions";
    }
} 