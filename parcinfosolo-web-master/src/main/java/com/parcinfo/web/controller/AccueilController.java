package com.parcinfo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Contrôleur principal gérant la page d'accueil de l'application.
 * Ce contrôleur est responsable de l'affichage de la page d'accueil
 * et des fonctionnalités de base de l'application.
 *
 * @author [Votre nom]
 * @version 1.0
 */
@Controller
public class AccueilController {

    /**
     * Affiche la page d'accueil de l'application.
     * Cette méthode est appelée lorsque l'utilisateur accède à la racine du site.
     *
     * @param model Le modèle Spring MVC pour passer les données à la vue
     * @return Le nom de la vue Thymeleaf à afficher ("index")
     */
    @GetMapping("/")
    public String accueil(Model model) {
        // Ajout de données pour la page d'accueil si nécessaire
        model.addAttribute("pageTitle", "Accueil - ParcInfo");
        return "index";
    }
} 