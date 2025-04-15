package com.parfinfo.dto.statistique;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatistiqueResponse {
    private Long id;
    private String type;
    private String description;
    private Map<String, Object> donnees;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String periode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 