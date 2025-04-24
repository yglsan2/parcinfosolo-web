package com.parcinfo.controller;

import com.parcinfo.model.OrdinateurPortable;
import com.parcinfo.model.TypeObjetNomade;
import com.parcinfo.service.ObjetNomadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur MVC pour la gestion des ordinateurs portables.
 */
@Controller
@RequestMapping("/ordinateurs-portables")
@RequiredArgsConstructor
public class OrdinateurPortableController {

    private final ObjetNomadeService objetNomadeService;

    /**
     * Affiche la liste des ordinateurs portables.
     *
     * @param model le modèle pour la vue
     * @return le nom de la vue
     */
    @GetMapping
    public String listOrdinateursPortables(Model model) {
        List<OrdinateurPortable> ordinateurs = objetNomadeService.getObjetsNomadesByType(TypeObjetNomade.LAPTOP)
                .stream()
                .filter(obj -> obj instanceof OrdinateurPortable)
                .map(obj -> (OrdinateurPortable) obj)
                .collect(Collectors.toList());
        model.addAttribute("ordinateurs", ordinateurs);
        return "ordinateurs-portables/list";
    }

    /**
     * Affiche le formulaire de création d'un ordinateur portable.
     *
     * @param model le modèle pour la vue
     * @return le nom de la vue
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("ordinateur", new OrdinateurPortable());
        return "ordinateurs-portables/form";
    }

    /**
     * Affiche le formulaire de modification d'un ordinateur portable.
     *
     * @param id l'ID de l'ordinateur portable
     * @param model le modèle pour la vue
     * @param redirectAttributes les attributs de redirection
     * @return le nom de la vue ou une redirection
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return objetNomadeService.getObjetNomadeById(id)
                .filter(obj -> obj instanceof OrdinateurPortable)
                .map(obj -> (OrdinateurPortable) obj)
                .map(ordinateur -> {
                    model.addAttribute("ordinateur", ordinateur);
                    return "ordinateurs-portables/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Ordinateur portable non trouvé");
                    return "redirect:/ordinateurs-portables";
                });
    }

    /**
     * Affiche les détails d'un ordinateur portable.
     *
     * @param id l'ID de l'ordinateur portable
     * @param model le modèle pour la vue
     * @param redirectAttributes les attributs de redirection
     * @return le nom de la vue ou une redirection
     */
    @GetMapping("/{id}")
    public String showDetails(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return objetNomadeService.getObjetNomadeById(id)
                .filter(obj -> obj instanceof OrdinateurPortable)
                .map(obj -> (OrdinateurPortable) obj)
                .map(ordinateur -> {
                    model.addAttribute("ordinateur", ordinateur);
                    return "ordinateurs-portables/details";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Ordinateur portable non trouvé");
                    return "redirect:/ordinateurs-portables";
                });
    }

    /**
     * Sauvegarde un ordinateur portable.
     *
     * @param ordinateur l'ordinateur portable à sauvegarder
     * @param redirectAttributes les attributs de redirection
     * @return une redirection
     */
    @PostMapping("/save")
    public String saveOrdinateurPortable(@ModelAttribute OrdinateurPortable ordinateur, RedirectAttributes redirectAttributes) {
        try {
            ordinateur.setType(TypeObjetNomade.LAPTOP);
            objetNomadeService.createObjetNomade(ordinateur);
            redirectAttributes.addFlashAttribute("success", "Ordinateur portable créé avec succès");
            return "redirect:/ordinateurs-portables";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/ordinateurs-portables/create";
        }
    }

    /**
     * Met à jour un ordinateur portable.
     *
     * @param id l'ID de l'ordinateur portable
     * @param ordinateur les nouvelles données de l'ordinateur portable
     * @param redirectAttributes les attributs de redirection
     * @return une redirection
     */
    @PostMapping("/{id}/update")
    public String updateOrdinateurPortable(@PathVariable Long id, @ModelAttribute OrdinateurPortable ordinateur, RedirectAttributes redirectAttributes) {
        try {
            ordinateur.setType(TypeObjetNomade.LAPTOP);
            objetNomadeService.updateObjetNomade(id, ordinateur);
            redirectAttributes.addFlashAttribute("success", "Ordinateur portable mis à jour avec succès");
            return "redirect:/ordinateurs-portables";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/ordinateurs-portables/" + id + "/edit";
        }
    }

    /**
     * Supprime un ordinateur portable.
     *
     * @param id l'ID de l'ordinateur portable
     * @param redirectAttributes les attributs de redirection
     * @return une redirection
     */
    @PostMapping("/{id}/delete")
    public String deleteOrdinateurPortable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            objetNomadeService.deleteObjetNomade(id);
            redirectAttributes.addFlashAttribute("success", "Ordinateur portable supprimé avec succès");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/ordinateurs-portables";
    }
} 