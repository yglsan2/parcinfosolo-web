package com.parfinfo.dto.export;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExportResponse {
    private Long id;
    private String type;
    private String format;
    private String statut;
    private String url;
    private String utilisateur;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private LocalDateTime dateCreation;
    private LocalDateTime dateCompletion;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 