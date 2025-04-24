package com.parcinfo.web.controller;

import com.parcinfo.web.service.PersonneService;
import com.parcinfo.web.service.OrdinateurService;
import com.parcinfo.web.service.PeripheriqueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebController {

    private final PersonneService personneService;
    private final OrdinateurService ordinateurService;
    private final PeripheriqueService peripheriqueService;

    public WebController(PersonneService personneService, 
                         OrdinateurService ordinateurService, 
                         PeripheriqueService peripheriqueService) {
        this.personneService = personneService;
        this.ordinateurService = ordinateurService;
        this.peripheriqueService = peripheriqueService;
    }

    @GetMapping
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/personnes")
    public String personnes(Model model) {
        model.addAttribute("personnes", personneService.findAll());
        return "personnes/index";
    }

    @GetMapping("/personnes/{id}")
    public String personneDetails(@PathVariable Long id, Model model) {
        model.addAttribute("personne", personneService.findById(id));
        return "personnes/show";
    }

    @GetMapping("/ordinateurs")
    public String ordinateurs(Model model) {
        model.addAttribute("ordinateurs", ordinateurService.findAll());
        return "ordinateurs/index";
    }

    @GetMapping("/peripheriques")
    public String peripheriques(Model model) {
        model.addAttribute("peripheriques", peripheriqueService.findAll());
        return "peripheriques/index";
    }
} 