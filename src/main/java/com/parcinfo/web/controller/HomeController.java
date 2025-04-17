package com.parcinfo.web.controller;

import com.parcinfo.model.Appareil;
import com.parcinfo.model.Personne;
import com.parcinfo.service.AppareilService;
import com.parcinfo.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PersonneService personneService;

    @Autowired
    private AppareilService appareilService;

    @GetMapping("/")
    public String home(Model model) {
        List<Personne> personnes = personneService.findAll();
        List<Appareil> appareils = appareilService.findAll();
        
        model.addAttribute("personnes", personnes);
        model.addAttribute("appareils", appareils);
        
        return "index";
    }
} 