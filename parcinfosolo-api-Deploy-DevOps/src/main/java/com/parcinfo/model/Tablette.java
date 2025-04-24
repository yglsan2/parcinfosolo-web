package com.parcinfo.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Tablette extends ObjetNomade {
    private Boolean supportClavier;
    private Boolean supportStylet;
} 