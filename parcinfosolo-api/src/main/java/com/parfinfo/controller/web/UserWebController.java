package com.parfinfo.controller.web;

import com.parfinfo.dto.user.CreateUserRequest;
import com.parfinfo.dto.user.UpdateUserRequest;
import com.parfinfo.dto.user.UserResponse;
import com.parfinfo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/users")
@RequiredArgsConstructor
public class UserWebController {

    private final UserService userService;

    @GetMapping
    public String listUsers(Model model, Pageable pageable) {
        model.addAttribute("users", userService.getAllUsers(pageable));
        return "users/list";
    }

    @GetMapping("/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("user", userService.getUserById(id));
            return "users/view";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new CreateUserRequest());
        return "users/form";
    }

    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable Long id, Model model) {
        try {
            UserResponse user = userService.getUserById(id);
            UpdateUserRequest updateRequest = new UpdateUserRequest();
            updateRequest.setUsername(user.getUsername());
            updateRequest.setEmail(user.getEmail());
            updateRequest.setNom(user.getNom());
            updateRequest.setPrenom(user.getPrenom());
            model.addAttribute("user", updateRequest);
            return "users/form";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping
    public String saveUser(@Valid @ModelAttribute("user") CreateUserRequest request, 
                         BindingResult result, 
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "users/form";
        }
        
        try {
            userService.createUser(request);
            redirectAttributes.addFlashAttribute("success", "Utilisateur créé avec succès");
            return "redirect:/web/users";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/users";
        }
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id,
                           @Valid @ModelAttribute("user") UpdateUserRequest request,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "users/form";
        }
        
        try {
            userService.updateUser(id, request);
            redirectAttributes.addFlashAttribute("success", "Utilisateur mis à jour avec succès");
            return "redirect:/web/users";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/web/users";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("success", "Utilisateur supprimé avec succès");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/web/users";
    }
}