package com.parfinfo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.parfinfo.service.GeoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/geo")
@Tag(name = "Géolocalisation", description = "API de géolocalisation et météo")
public class GeoController {
    private final GeoService geoService;

    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }

    @GetMapping("/coordinates")
    @Operation(summary = "Obtenir les coordonnées d'une adresse")
    public Mono<ResponseEntity<JsonNode>> getCoordinates(
            @Parameter(description = "Adresse à géocoder") 
            @RequestParam String address) {
        return geoService.getCoordinates(address)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/weather")
    @Operation(summary = "Obtenir la météo pour des coordonnées")
    public Mono<ResponseEntity<JsonNode>> getWeather(
            @Parameter(description = "Latitude") 
            @RequestParam double lat,
            @Parameter(description = "Longitude") 
            @RequestParam double lon) {
        return geoService.getWeather(lat, lon)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
} 