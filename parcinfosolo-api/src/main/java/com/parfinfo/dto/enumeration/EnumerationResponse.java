package com.parfinfo.dto.enumeration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnumerationResponse {
    private Long id;
    private String type;
    private String description;
    private List<String> valeurs;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 