package com.parfinfo.dto.peripherique;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PeripheriqueHistoryResponse {
    private Long id;
    private Long peripheriqueId;
    private String typeAction;
    private String description;
    private String ancienStatut;
    private String nouveauStatut;
    private String utilisateur;
    private LocalDateTime dateAction;
    private String details;
} 