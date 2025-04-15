package com.parfinfo.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeoService {
    private final WebClient webClient;
    private final String openWeatherApiKey;

    public GeoService(@Value("${openweather.api.key}") String openWeatherApiKey) {
        this.webClient = WebClient.builder().build();
        this.openWeatherApiKey = openWeatherApiKey;
    }

    public Mono<JsonNode> getCoordinates(String address) {
        String encodedAddress = address.replace(" ", "+");
        return webClient.get()
                .uri("https://nominatim.openstreetmap.org/search?format=json&q=" + encodedAddress)
                .header("User-Agent", "ParfInfo Application")
                .retrieve()
                .bodyToMono(JsonNode.class);
    }

    public Mono<JsonNode> getWeather(double lat, double lon) {
        return webClient.get()
                .uri("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + 
                     "&lon=" + lon + "&appid=" + openWeatherApiKey + "&units=metric&lang=fr")
                .retrieve()
                .bodyToMono(JsonNode.class);
    }
} 