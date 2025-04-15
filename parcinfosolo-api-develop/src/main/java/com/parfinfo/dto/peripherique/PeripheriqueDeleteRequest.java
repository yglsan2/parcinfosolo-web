package com.parfinfo.dto.peripherique;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PeripheriqueDeleteRequest {
    
    @NotNull(message = "L'ID est obligatoire")
    private Long id;
    
    private String raison;
} 