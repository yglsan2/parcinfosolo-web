package com.parcinfo.web.controller;

import com.parcinfo.web.model.Personne;
import com.parcinfo.web.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Contrôleur gérant les opérations liées aux personnes dans l'application.
 * Ce contrôleur gère les vues Thymeleaf pour l'affichage et la manipulation des personnes.
 * 
 * Les endpoints suivent le pattern RESTful tout en retournant des vues Thymeleaf :
 * - GET /personnes : Liste toutes les personnes
 * - GET /personnes/new : Affiche le formulaire de création
 * - GET /personnes/{id} : Affiche les détails d'une personne
 * - GET /personnes/{id}/edit : Affiche le formulaire d'édition
 * - POST /personnes : Crée une nouvelle personne
 * - POST /personnes/{id} : Met à jour une personne existante
 * - GET /personnes/{id}/delete : Supprime une personne
 *
 * @author [Votre nom]
 * @version 1.0
 */
@Controller
@RequestMapping("/personnes")
public class PersonneController {

    @Autowired
    private PersonneService personneService;

    /**
     * Affiche la liste de toutes les personnes.
     * Cette méthode récupère toutes les personnes via le service et les passe au modèle
     * pour l'affichage dans la vue Thymeleaf.
     *
     * @param model Le modèle Spring MVC pour passer les données à la vue
     * @return Le nom de la vue Thymeleaf à afficher ("personnes/index")
     */
    @GetMapping
    public String listPersonnes(Model model) {
        model.addAttribute("personnes", personneService.findAll());
        return "personnes/index";
    }

    /**
     * Affiche le formulaire de création d'une nouvelle personne.
     * Initialise un nouvel objet Personne et le passe au modèle pour le formulaire.
     *
     * @param model Le modèle Spring MVC pour passer les données au formulaire
     * @return Le nom de la vue Thymeleaf du formulaire ("personnes/form")
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("personne", new Personne());
        return "personnes/form";
    }

    /**
     * Affiche les détails d'une personne spécifique.
     * Récupère la personne par son ID et la passe au modèle pour l'affichage.
     *
     * @param id L'identifiant de la personne à afficher
     * @param model Le modèle Spring MVC pour passer les données à la vue
     * @return Le nom de la vue Thymeleaf à afficher ("personnes/show")
     * @throws RuntimeException si la personne n'est pas trouvée
     */
    @GetMapping("/{id}")
    public String viewPersonne(@PathVariable Long id, Model model) {
        Personne personne = personneService.findById(id);
        model.addAttribute("personne", personne);
        return "personnes/show";
    }

    /**
     * Affiche le formulaire d'édition d'une personne existante.
     * Récupère la personne par son ID et la passe au modèle pour le formulaire.
     *
     * @param id L'identifiant de la personne à éditer
     * @param model Le modèle Spring MVC pour passer les données au formulaire
     * @return Le nom de la vue Thymeleaf du formulaire ("personnes/form")
     * @throws RuntimeException si la personne n'est pas trouvée
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Personne personne = personneService.findById(id);
        model.addAttribute("personne", personne);
        return "personnes/form";
    }

    /**
     * Crée une nouvelle personne.
     * Traite les données du formulaire et sauvegarde la nouvelle personne.
     * Utilise RedirectAttributes pour passer des messages flash à la vue suivante.
     *
     * @param personne L'objet Personne créé à partir des données du formulaire
     * @param redirectAttributes Les attributs pour la redirection (messages flash)
     * @return Une redirection vers la liste des personnes
     */
    @PostMapping
    public String createPersonne(@ModelAttribute Personne personne, RedirectAttributes redirectAttributes) {
        try {
            personneService.save(personne);
            redirectAttributes.addFlashAttribute("message", "Personne créée avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la création de la personne");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/personnes";
    }

    /**
     * Met à jour une personne existante.
     * Traite les données du formulaire et met à jour la personne.
     * Utilise RedirectAttributes pour passer des messages flash à la vue suivante.
     *
     * @param id L'identifiant de la personne à mettre à jour
     * @param personne L'objet Personne avec les nouvelles données
     * @param redirectAttributes Les attributs pour la redirection (messages flash)
     * @return Une redirection vers la liste des personnes
     */
    @PostMapping("/{id}")
    public String updatePersonne(@PathVariable Long id, @ModelAttribute Personne personne, RedirectAttributes redirectAttributes) {
        try {
            personne.setIdPersonne(id);
            personneService.save(personne);
            redirectAttributes.addFlashAttribute("message", "Personne mise à jour avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la mise à jour de la personne");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/personnes";
    }

    /**
     * Supprime une personne.
     * Supprime la personne par son ID et affiche un message de confirmation.
     *
     * @param id L'identifiant de la personne à supprimer
     * @param redirectAttributes Les attributs pour la redirection (messages flash)
     * @return Une redirection vers la liste des personnes
     */
    @GetMapping("/{id}/delete")
    public String deletePersonne(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            personneService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Personne supprimée avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la suppression de la personne");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/personnes";
    }
} 