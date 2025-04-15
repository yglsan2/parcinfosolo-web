package com.parfinfo.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.parfinfo.service.UserService;
import com.parfinfo.model.User;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/web/users")
@RequiredArgsConstructor
public class UserWebController {

    private final UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé")));
        return "users/view";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }

    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé")));
        return "users/form";
    }

    @PostMapping
    public String saveUser(@ModelAttribute User user) {
        if (user.getId() == null) {
            userService.createUser(user);
        } else {
            userService.updateUser(user.getId(), user);
        }
        return "redirect:/web/users";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/web/users";
    }
} 