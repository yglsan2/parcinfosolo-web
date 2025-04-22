package com.parcinfo.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Smartphone extends ObjetNomade {
    private Boolean aDoubleSim;
    private Boolean aNfc;
    private Boolean a5g;
} 