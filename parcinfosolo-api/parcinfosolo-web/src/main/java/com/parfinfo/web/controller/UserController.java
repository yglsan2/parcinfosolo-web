package com.parfinfo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final RestTemplate restTemplate;
    
    @Value("${api.base-url}")
    private String apiBaseUrl;

    @GetMapping
    public String listUsers(Model model) {
        List<Map<String, Object>> users = restTemplate.getForObject(
            apiBaseUrl + "/users", 
            List.class
        );
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        Map<String, Object> user = restTemplate.getForObject(
            apiBaseUrl + "/users/" + id,
            Map.class
        );
        model.addAttribute("user", user);
        return "users/view";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", Map.of());
        return "users/form";
    }

    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable Long id, Model model) {
        Map<String, Object> user = restTemplate.getForObject(
            apiBaseUrl + "/users/" + id,
            Map.class
        );
        model.addAttribute("user", user);
        return "users/form";
    }

    @PostMapping
    public String saveUser(@ModelAttribute Map<String, Object> user) {
        if (user.get("id") == null) {
            restTemplate.postForObject(apiBaseUrl + "/users", user, Map.class);
        } else {
            restTemplate.put(apiBaseUrl + "/users/" + user.get("id"), user);
        }
        return "redirect:/users";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        restTemplate.delete(apiBaseUrl + "/users/" + id);
        return "redirect:/users";
    }
} 