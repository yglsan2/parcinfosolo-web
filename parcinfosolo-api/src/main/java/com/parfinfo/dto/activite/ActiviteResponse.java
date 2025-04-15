package com.parfinfo.dto.activite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiviteResponse {
    private Long id;
    private String type;
    private String description;
    private String utilisateur;
    private String entite;
    private Long entiteId;
    private LocalDateTime dateActivite;
    private String details;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 