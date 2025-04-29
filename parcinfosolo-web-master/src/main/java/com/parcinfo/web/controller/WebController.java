package com.parcinfo.web.controller;

import com.parcinfo.web.service.PersonneService;
import com.parcinfo.web.service.OrdinateurService;
import com.parcinfo.web.service.PeripheriqueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contrôleur principal gérant les pages web de l'application.
 * Ce contrôleur est responsable de l'affichage des pages principales
 * et de la navigation entre les différentes sections de l'application.
 *
 * @author [Votre nom]
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class WebController {

    private final PersonneService personneService;
    private final OrdinateurService ordinateurService;
    private final PeripheriqueService peripheriqueService;

    /**
     * Constructeur du contrôleur avec injection des dépendances.
     *
     * @param personneService Service de gestion des personnes
     * @param ordinateurService Service de gestion des ordinateurs
     * @param peripheriqueService Service de gestion des périphériques
     */
    public WebController(PersonneService personneService, 
                         OrdinateurService ordinateurService, 
                         PeripheriqueService peripheriqueService) {
        this.personneService = personneService;
        this.ordinateurService = ordinateurService;
        this.peripheriqueService = peripheriqueService;
    }

    /**
     * Affiche la page d'accueil de l'application.
     *
     * @param model Le modèle Spring MVC pour passer les données à la vue
     * @return Le nom de la vue Thymeleaf à afficher ("index")
     */
    @GetMapping
    public String index(Model model) {
        model.addAttribute("pageTitle", "Accueil - ParcInfo");
        return "index";
    }

    /**
     * Affiche la page de connexion.
     *
     * @return Le nom de la vue Thymeleaf à afficher ("login")
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Affiche la page d'inscription.
     *
     * @return Le nom de la vue Thymeleaf à afficher ("register")
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * Affiche la liste de toutes les personnes.
     *
     * @param model Le modèle Spring MVC pour passer les données à la vue
     * @return Le nom de la vue Thymeleaf à afficher ("personnes/index")
     */
    @GetMapping("/personnes")
    public String personnes(Model model) {
        model.addAttribute("personnes", personneService.findAll());
        model.addAttribute("pageTitle", "Liste des personnes - ParcInfo");
        return "personnes/index";
    }

    /**
     * Affiche les détails d'une personne spécifique.
     *
     * @param id L'identifiant de la personne à afficher
     * @param model Le modèle Spring MVC pour passer les données à la vue
     * @return Le nom de la vue Thymeleaf à afficher ("personnes/show")
     */
    @GetMapping("/personnes/{id}")
    public String personneDetails(@PathVariable Long id, Model model) {
        model.addAttribute("personne", personneService.findById(id));
        model.addAttribute("pageTitle", "Détails de la personne - ParcInfo");
        return "personnes/show";
    }

    /**
     * Affiche la liste de tous les ordinateurs.
     *
     * @param model Le modèle Spring MVC pour passer les données à la vue
     * @return Le nom de la vue Thymeleaf à afficher ("ordinateurs/index")
     */
    @GetMapping("/ordinateurs")
    public String ordinateurs(Model model) {
        model.addAttribute("ordinateurs", ordinateurService.findAll());
        model.addAttribute("pageTitle", "Liste des ordinateurs - ParcInfo");
        return "ordinateurs/index";
    }

    /**
     * Affiche la liste de tous les périphériques.
     *
     * @param model Le modèle Spring MVC pour passer les données à la vue
     * @return Le nom de la vue Thymeleaf à afficher ("peripheriques/index")
     */
    @GetMapping("/peripheriques")
    public String peripheriques(Model model) {
        model.addAttribute("peripheriques", peripheriqueService.findAll());
        model.addAttribute("pageTitle", "Liste des périphériques - ParcInfo");
        return "peripheriques/index";
    }
} 