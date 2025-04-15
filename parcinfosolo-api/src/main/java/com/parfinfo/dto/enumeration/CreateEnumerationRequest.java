package com.parfinfo.dto.enumeration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEnumerationRequest {
    @NotBlank(message = "Le type est obligatoire")
    private String type;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotEmpty(message = "Les valeurs sont obligatoires")
    private List<String> valeurs;
} 