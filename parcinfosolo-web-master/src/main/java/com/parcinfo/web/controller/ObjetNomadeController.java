package com.parcinfo.web.controller;

import com.parcinfo.web.model.ObjetNomade;
import com.parcinfo.web.service.ObjetNomadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Contrôleur gérant les opérations liées aux objets nomades dans l'application.
 * Ce contrôleur gère les vues Thymeleaf pour l'affichage et la manipulation des objets nomades.
 * 
 * Les endpoints suivent le pattern RESTful tout en retournant des vues Thymeleaf :
 * - GET /objets-nomades : Liste tous les objets nomades
 * - GET /objets-nomades/new : Affiche le formulaire de création
 * - GET /objets-nomades/{id} : Affiche les détails d'un objet nomade
 * - GET /objets-nomades/{id}/edit : Affiche le formulaire d'édition
 * - POST /objets-nomades : Crée un nouvel objet nomade
 * - POST /objets-nomades/{id} : Met à jour un objet nomade existant
 * - GET /objets-nomades/{id}/delete : Supprime un objet nomade
 *
 * @author ParcInfo Team
 * @version 1.0
 */
@Controller
@RequestMapping("/objets-nomades")
public class ObjetNomadeController {

    /**
     * Service pour la gestion des objets nomades.
     * Injecté automatiquement par Spring.
     */
    @Autowired
    private ObjetNomadeService objetNomadeService;

    /**
     * Affiche la liste de tous les objets nomades.
     * Cette méthode récupère tous les objets nomades via le service et les passe au modèle
     * pour l'affichage dans la vue Thymeleaf.
     *
     * @param model Le modèle Spring MVC pour passer les données à la vue
     * @return Le nom de la vue Thymeleaf à afficher ("objets-nomades/list")
     */
    @GetMapping
    public String listObjetsNomades(Model model) {
        model.addAttribute("objetsNomades", objetNomadeService.findAll());
        return "objets-nomades/list";
    }

    /**
     * Affiche le formulaire de création d'un nouvel objet nomade.
     * Initialise un nouvel objet ObjetNomade et le passe au modèle pour le formulaire.
     *
     * @param model Le modèle Spring MVC pour passer les données au formulaire
     * @return Le nom de la vue Thymeleaf du formulaire ("objets-nomades/form")
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("objetNomade", new ObjetNomade());
        return "objets-nomades/form";
    }

    /**
     * Affiche les détails d'un objet nomade spécifique.
     * Récupère l'objet nomade par son ID et le passe au modèle pour l'affichage.
     *
     * @param id L'identifiant de l'objet nomade à afficher
     * @param model Le modèle Spring MVC pour passer les données à la vue
     * @return Le nom de la vue Thymeleaf à afficher ("objets-nomades/view")
     * @throws RuntimeException si l'objet nomade n'est pas trouvé
     */
    @GetMapping("/{id}")
    public String viewObjetNomade(@PathVariable Long id, Model model) {
        ObjetNomade objetNomade = objetNomadeService.findById(id);
        model.addAttribute("objetNomade", objetNomade);
        return "objets-nomades/view";
    }

    /**
     * Affiche le formulaire d'édition d'un objet nomade existant.
     * Récupère l'objet nomade par son ID et le passe au modèle pour le formulaire.
     *
     * @param id L'identifiant de l'objet nomade à éditer
     * @param model Le modèle Spring MVC pour passer les données au formulaire
     * @return Le nom de la vue Thymeleaf du formulaire ("objets-nomades/form")
     * @throws RuntimeException si l'objet nomade n'est pas trouvé
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        ObjetNomade objetNomade = objetNomadeService.findById(id);
        model.addAttribute("objetNomade", objetNomade);
        return "objets-nomades/form";
    }

    /**
     * Crée un nouvel objet nomade.
     * Traite les données du formulaire et sauvegarde le nouvel objet nomade.
     * Utilise RedirectAttributes pour passer des messages flash à la vue suivante.
     *
     * @param objetNomade L'objet ObjetNomade créé à partir des données du formulaire
     * @param redirectAttributes Les attributs pour la redirection (messages flash)
     * @return Une redirection vers la liste des objets nomades
     */
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

    /**
     * Met à jour un objet nomade existant.
     * Traite les données du formulaire et met à jour l'objet nomade.
     * Utilise RedirectAttributes pour passer des messages flash à la vue suivante.
     *
     * @param id L'identifiant de l'objet nomade à mettre à jour
     * @param objetNomade L'objet ObjetNomade avec les nouvelles données
     * @param redirectAttributes Les attributs pour la redirection (messages flash)
     * @return Une redirection vers la liste des objets nomades
     */
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

    /**
     * Supprime un objet nomade.
     * Supprime l'objet nomade par son ID et affiche un message de confirmation.
     *
     * @param id L'identifiant de l'objet nomade à supprimer
     * @param redirectAttributes Les attributs pour la redirection (messages flash)
     * @return Une redirection vers la liste des objets nomades
     */
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