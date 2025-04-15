package com.parfinfo.dto.activite;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ActiviteResponse {
    private Long id;
    private String type;
    private String description;
    private LocalDateTime dateCreation;
    private Long utilisateurId;
    private String utilisateurNom;
    private Long appareilId;
    private String appareilNom;
} 