package com.parcinfo.web.controller;

import com.parcinfo.web.model.Peripherique;
import com.parcinfo.web.model.Intervention;
import com.parcinfo.web.service.PeripheriqueService;
import com.parcinfo.web.service.InterventionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

/**
 * Contrôleur pour la gestion des périphériques et leurs interventions associées.
 * Gère les requêtes HTTP liées aux opérations CRUD sur les périphériques et leurs interventions.
 * 
 * Les endpoints suivent le pattern RESTful tout en retournant des vues Thymeleaf :
 * - GET /peripheriques : Liste tous les périphériques
 * - GET /peripheriques/new : Affiche le formulaire de création
 * - GET /peripheriques/{id} : Affiche les détails d'un périphérique
 * - GET /peripheriques/{id}/edit : Affiche le formulaire d'édition
 * - POST /peripheriques : Crée un nouveau périphérique
 * - POST /peripheriques/{id} : Met à jour un périphérique existant
 * - GET /peripheriques/{id}/delete : Supprime un périphérique
 * - GET /peripheriques/{id}/interventions : Liste les interventions d'un périphérique
 * - POST /peripheriques/{id}/interventions : Ajoute une intervention
 * - GET /peripheriques/{id}/interventions/{interventionId}/edit : Édite une intervention
 * - POST /peripheriques/{id}/interventions/{interventionId} : Met à jour une intervention
 * - POST /peripheriques/{id}/interventions/{interventionId}/delete : Supprime une intervention
 *
 * @author ParcInfo Team
 * @version 1.0
 */
@Controller
@RequestMapping("/peripheriques")
public class PeripheriqueController {

    /**
     * Service pour la gestion des périphériques.
     * Injecté via le constructeur.
     */
    private final PeripheriqueService peripheriqueService;

    /**
     * Service pour la gestion des interventions.
     * Injecté via le constructeur.
     */
    private final InterventionService interventionService;

    /**
     * Constructeur du contrôleur avec injection des dépendances.
     *
     * @param peripheriqueService Service de gestion des périphériques
     * @param interventionService Service de gestion des interventions
     */
    @Autowired
    public PeripheriqueController(PeripheriqueService peripheriqueService, InterventionService interventionService) {
        this.peripheriqueService = peripheriqueService;
        this.interventionService = interventionService;
    }

    /**
     * Affiche la liste de tous les périphériques.
     * Récupère tous les périphériques via le service et les passe au modèle
     * pour l'affichage dans la vue Thymeleaf.
     *
     * @param model Le modèle Spring MVC pour passer les données à la vue
     * @return Le nom de la vue Thymeleaf à afficher ("peripheriques/index")
     */
    @GetMapping
    public String listPeripheriques(Model model) {
        model.addAttribute("peripheriques", peripheriqueService.findAll());
        return "peripheriques/index";
    }

    /**
     * Affiche le formulaire de création d'un nouveau périphérique.
     * Initialise un nouvel objet Peripherique et le passe au modèle pour le formulaire.
     *
     * @param model Le modèle Spring MVC pour passer les données au formulaire
     * @return Le nom de la vue Thymeleaf du formulaire ("peripheriques/form")
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("peripherique", new Peripherique());
        return "peripheriques/form";
    }

    /**
     * Crée un nouveau périphérique.
     * Traite les données du formulaire et sauvegarde le nouveau périphérique.
     *
     * @param peripherique Le périphérique à créer, créé à partir des données du formulaire
     * @return Une redirection vers la liste des périphériques
     */
    @PostMapping
    public String createPeripherique(@ModelAttribute Peripherique peripherique) {
        peripheriqueService.save(peripherique);
        return "redirect:/peripheriques";
    }

    /**
     * Affiche les détails d'un périphérique spécifique.
     * Récupère le périphérique par son ID et le passe au modèle pour l'affichage.
     *
     * @param id L'identifiant du périphérique à afficher
     * @param model Le modèle Spring MVC pour passer les données à la vue
     * @return Le nom de la vue Thymeleaf à afficher ("peripheriques/show")
     * @throws RuntimeException si le périphérique n'est pas trouvé
     */
    @GetMapping("/{id}")
    public String viewPeripherique(@PathVariable Long id, Model model) {
        Peripherique peripherique = peripheriqueService.findById(id);
        model.addAttribute("peripherique", peripherique);
        return "peripheriques/show";
    }

    /**
     * Affiche le formulaire d'édition d'un périphérique existant.
     * Récupère le périphérique par son ID et le passe au modèle pour le formulaire.
     *
     * @param id L'identifiant du périphérique à éditer
     * @param model Le modèle Spring MVC pour passer les données au formulaire
     * @return Le nom de la vue Thymeleaf du formulaire ("peripheriques/form")
     * @throws RuntimeException si le périphérique n'est pas trouvé
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Peripherique peripherique = peripheriqueService.findById(id);
        model.addAttribute("peripherique", peripherique);
        return "peripheriques/form";
    }

    /**
     * Met à jour un périphérique existant.
     * Traite les données du formulaire et met à jour le périphérique.
     *
     * @param id L'identifiant du périphérique à mettre à jour
     * @param peripherique Les nouvelles données du périphérique
     * @return Une redirection vers la liste des périphériques
     * @throws RuntimeException si le périphérique n'est pas trouvé
     */
    @PostMapping("/{id}")
    public String updatePeripherique(@PathVariable Long id, @ModelAttribute Peripherique peripherique) {
        peripheriqueService.update(id, peripherique);
        return "redirect:/peripheriques";
    }

    /**
     * Supprime un périphérique.
     * Supprime le périphérique par son ID.
     *
     * @param id L'identifiant du périphérique à supprimer
     * @return Une redirection vers la liste des périphériques
     * @throws RuntimeException si le périphérique n'est pas trouvé
     */
    @GetMapping("/{id}/delete")
    public String deletePeripherique(@PathVariable Long id) {
        peripheriqueService.deleteById(id);
        return "redirect:/peripheriques";
    }

    /**
     * Affiche la liste des interventions d'un périphérique spécifique.
     * Récupère le périphérique et ses interventions associées pour l'affichage.
     *
     * @param id L'identifiant du périphérique
     * @param model Le modèle Spring MVC pour passer les données à la vue
     * @return Le nom de la vue Thymeleaf à afficher ("peripheriques/interventions")
     * @throws RuntimeException si le périphérique n'est pas trouvé
     */
    @GetMapping("/{id}/interventions")
    public String listInterventions(@PathVariable Long id, Model model) {
        Peripherique peripherique = peripheriqueService.findById(id);
        model.addAttribute("peripherique", peripherique);
        model.addAttribute("interventions", interventionService.findByPeripheriqueId(id));
        return "peripheriques/interventions";
    }

    /**
     * Ajoute une nouvelle intervention à un périphérique.
     * Valide les données de l'intervention et met à jour la date de dernière maintenance
     * si l'intervention est de type "MAINTENANCE".
     *
     * @param id L'identifiant du périphérique
     * @param intervention L'intervention à ajouter
     * @param result Le résultat de la validation
     * @param redirectAttributes Les attributs pour la redirection (messages flash)
     * @return Une redirection vers la liste des interventions du périphérique
     * @throws RuntimeException si le périphérique n'est pas trouvé
     */
    @PostMapping("/{id}/interventions")
    public String addIntervention(@PathVariable Long id, @Valid Intervention intervention, 
                                BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "peripheriques/interventions";
        }

        Peripherique peripherique = peripheriqueService.findById(id);
        
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

    /**
     * Affiche le formulaire d'édition d'une intervention existante.
     * Récupère l'intervention et le périphérique associé pour l'édition.
     *
     * @param id L'identifiant du périphérique
     * @param interventionId L'identifiant de l'intervention à éditer
     * @param model Le modèle Spring MVC pour passer les données au formulaire
     * @return Le nom de la vue Thymeleaf du formulaire ("peripheriques/intervention-edit")
     * @throws RuntimeException si le périphérique ou l'intervention n'est pas trouvé
     */
    @GetMapping("/{id}/interventions/{interventionId}/edit")
    public String showEditInterventionForm(@PathVariable Long id, @PathVariable Long interventionId, Model model) {
        Peripherique peripherique = peripheriqueService.findById(id);
        Intervention intervention = interventionService.findById(interventionId);
        
        model.addAttribute("peripherique", peripherique);
        model.addAttribute("intervention", intervention);
        return "peripheriques/intervention-edit";
    }

    /**
     * Met à jour une intervention existante.
     * Valide les nouvelles données de l'intervention et met à jour l'intervention existante.
     *
     * @param id L'identifiant du périphérique
     * @param interventionId L'identifiant de l'intervention à mettre à jour
     * @param intervention Les nouvelles données de l'intervention
     * @param result Le résultat de la validation
     * @param redirectAttributes Les attributs pour la redirection (messages flash)
     * @return Une redirection vers la liste des interventions du périphérique
     * @throws RuntimeException si l'intervention n'est pas trouvée
     */
    @PostMapping("/{id}/interventions/{interventionId}")
    public String updateIntervention(@PathVariable Long id, @PathVariable Long interventionId,
                                   @Valid Intervention intervention, BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "peripheriques/intervention-edit";
        }

        Intervention existingIntervention = interventionService.findById(interventionId);
        
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

    /**
     * Supprime une intervention existante.
     * Supprime l'intervention par son ID.
     *
     * @param id L'identifiant du périphérique
     * @param interventionId L'identifiant de l'intervention à supprimer
     * @param redirectAttributes Les attributs pour la redirection (messages flash)
     * @return Une redirection vers la liste des interventions du périphérique
     * @throws RuntimeException si l'intervention n'est pas trouvée
     */
    @PostMapping("/{id}/interventions/{interventionId}/delete")
    public String deleteIntervention(@PathVariable Long id, @PathVariable Long interventionId,
                                   RedirectAttributes redirectAttributes) {
        interventionService.deleteById(interventionId);
        redirectAttributes.addFlashAttribute("message", "L'intervention a été supprimée avec succès.");
        return "redirect:/peripheriques/" + id + "/interventions";
    }
} 