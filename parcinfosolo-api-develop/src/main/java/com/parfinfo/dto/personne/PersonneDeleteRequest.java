package com.parfinfo.dto.personne;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonneDeleteRequest {
    
    @NotNull(message = "L'ID est obligatoire")
    private Long id;
    
    private String raison;
} 