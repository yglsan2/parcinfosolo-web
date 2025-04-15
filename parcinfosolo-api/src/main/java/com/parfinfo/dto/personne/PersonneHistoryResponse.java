package com.parfinfo.dto.personne;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PersonneHistoryResponse {
    private Long id;
    private Long personneId;
    private String typeAction;
    private String description;
    private String ancienStatut;
    private String nouveauStatut;
    private String utilisateur;
    private LocalDateTime dateAction;
    private String details;
} 