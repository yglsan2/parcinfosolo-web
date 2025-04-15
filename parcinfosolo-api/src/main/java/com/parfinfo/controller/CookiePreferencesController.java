package com.parfinfo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cookie-preferences")
public class CookiePreferencesController {

    @PostMapping
    public ResponseEntity<?> saveCookiePreferences(@RequestBody Map<String, Boolean> preferences) {
        // TODO: Implémenter la sauvegarde des préférences
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, Boolean>> getCookiePreferences() {
        // TODO: Implémenter la récupération des préférences
        return ResponseEntity.ok(Map.of(
            "necessary", true,
            "analytics", false,
            "marketing", false
        ));
    }
} 