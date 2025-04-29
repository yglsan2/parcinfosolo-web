package com.parcinfo.web.controller;

import com.parcinfo.web.model.Intervention;
import com.parcinfo.web.service.InterventionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Contrôleur gérant les opérations liées aux interventions dans l'application.
 * Ce contrôleur gère les vues Thymeleaf pour l'affichage et la manipulation des interventions.
 * 
 * Les endpoints suivent le pattern RESTful tout en retournant des vues Thymeleaf :
 * - GET /interventions : Liste toutes les interventions
 * - GET /interventions/new : Affiche le formulaire de création
 * - GET /interventions/{id} : Affiche les détails d'une intervention
 * - GET /interventions/{id}/edit : Affiche le formulaire d'édition
 * - POST /interventions : Crée une nouvelle intervention
 * - POST /interventions/{id} : Met à jour une intervention existante
 * - GET /interventions/{id}/delete : Supprime une intervention
 *
 * @author [Votre nom]
 * @version 1.0
 */
@Controller
@RequestMapping("/interventions")
public class InterventionController {

    @Autowired
    private InterventionService interventionService;

    /**
     * Affiche la liste de toutes les interventions.
     * Cette méthode récupère toutes les interventions via le service et les passe au modèle
     * pour l'affichage dans la vue Thymeleaf.
     *
     * @param model Le modèle Spring MVC pour passer les données à la vue
     * @return Le nom de la vue Thymeleaf à afficher ("interventions/index")
     */
    @GetMapping
    public String listInterventions(Model model) {
        model.addAttribute("interventions", interventionService.findAll());
        return "interventions/index";
    }

    /**
     * Affiche le formulaire de création d'une nouvelle intervention.
     * Initialise un nouvel objet Intervention et le passe au modèle pour le formulaire.
     *
     * @param model Le modèle Spring MVC pour passer les données au formulaire
     * @return Le nom de la vue Thymeleaf du formulaire ("interventions/form")
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("intervention", new Intervention());
        return "interventions/form";
    }

    /**
     * Affiche les détails d'une intervention spécifique.
     * Récupère l'intervention par son ID et la passe au modèle pour l'affichage.
     *
     * @param id L'identifiant de l'intervention à afficher
     * @param model Le modèle Spring MVC pour passer les données à la vue
     * @return Le nom de la vue Thymeleaf à afficher ("interventions/show")
     * @throws RuntimeException si l'intervention n'est pas trouvée
     */
    @GetMapping("/{id}")
    public String viewIntervention(@PathVariable Long id, Model model) {
        Intervention intervention = interventionService.findById(id);
        model.addAttribute("intervention", intervention);
        return "interventions/show";
    }

    /**
     * Affiche le formulaire d'édition d'une intervention existante.
     * Récupère l'intervention par son ID et la passe au modèle pour le formulaire.
     *
     * @param id L'identifiant de l'intervention à éditer
     * @param model Le modèle Spring MVC pour passer les données au formulaire
     * @return Le nom de la vue Thymeleaf du formulaire ("interventions/form")
     * @throws RuntimeException si l'intervention n'est pas trouvée
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Intervention intervention = interventionService.findById(id);
        model.addAttribute("intervention", intervention);
        return "interventions/form";
    }

    /**
     * Crée une nouvelle intervention.
     * Traite les données du formulaire et sauvegarde la nouvelle intervention.
     * Utilise RedirectAttributes pour passer des messages flash à la vue suivante.
     *
     * @param intervention L'objet Intervention créé à partir des données du formulaire
     * @param bindingResult Le résultat de la validation du formulaire
     * @param redirectAttributes Les attributs pour la redirection (messages flash)
     * @return Une redirection vers la liste des interventions
     */
    @PostMapping
    public String createIntervention(@Valid @ModelAttribute Intervention intervention,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Erreur de validation");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            return "interventions/form";
        }

        try {
            interventionService.save(intervention);
            redirectAttributes.addFlashAttribute("message", "Intervention créée avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la création de l'intervention");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/interventions";
    }

    /**
     * Met à jour une intervention existante.
     * Traite les données du formulaire et met à jour l'intervention.
     * Utilise RedirectAttributes pour passer des messages flash à la vue suivante.
     *
     * @param id L'identifiant de l'intervention à mettre à jour
     * @param intervention L'objet Intervention avec les nouvelles données
     * @param bindingResult Le résultat de la validation du formulaire
     * @param redirectAttributes Les attributs pour la redirection (messages flash)
     * @return Une redirection vers la liste des interventions
     */
    @PostMapping("/{id}")
    public String updateIntervention(@PathVariable Long id,
                                   @Valid @ModelAttribute Intervention intervention,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Erreur de validation");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            return "interventions/form";
        }

        try {
            intervention.setId(id);
            interventionService.save(intervention);
            redirectAttributes.addFlashAttribute("message", "Intervention mise à jour avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la mise à jour de l'intervention");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/interventions";
    }

    /**
     * Supprime une intervention.
     * Supprime l'intervention par son ID et affiche un message de confirmation.
     *
     * @param id L'identifiant de l'intervention à supprimer
     * @param redirectAttributes Les attributs pour la redirection (messages flash)
     * @return Une redirection vers la liste des interventions
     */
    @GetMapping("/{id}/delete")
    public String deleteIntervention(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            interventionService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Intervention supprimée avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la suppression de l'intervention");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/interventions";
    }
} 