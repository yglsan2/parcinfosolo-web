package com.parfinfo.controller;

import com.parfinfo.service.EnumerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enumerations")
public class EnumerationController {

    @Autowired
    private EnumerationService enumerationService;

    @GetMapping("/types")
    public ResponseEntity<List<String>> getTypes() {
        return ResponseEntity.ok(enumerationService.getTypes());
    }

    @GetMapping("/etats")
    public ResponseEntity<List<String>> getEtats() {
        return ResponseEntity.ok(enumerationService.getEtats());
    }

    @GetMapping("/localisations")
    public ResponseEntity<List<String>> getLocalisations() {
        return ResponseEntity.ok(enumerationService.getLocalisations());
    }
} 