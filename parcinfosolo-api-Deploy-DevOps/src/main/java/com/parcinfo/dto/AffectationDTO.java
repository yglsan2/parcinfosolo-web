package com.parcinfo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AffectationDTO {
    private Long id;
    private Long personneId;
    private String personneNom;
    private String personnePrenom;
    private Long appareilId;
    private String appareilType;
    private String appareilLibelle;
    private LocalDate dateAffectation;
} 