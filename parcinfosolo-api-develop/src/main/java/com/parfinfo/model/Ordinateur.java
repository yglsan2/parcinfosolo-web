package com.parfinfo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ordinateur")
public class Ordinateur extends Appareil {
    @Column(name = "de_bureau")
    private boolean deBureau;

    @JsonManagedReference("ordinateur-peripheriques")
    @OneToMany(mappedBy = "ordinateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Peripherique> peripheriques = new HashSet<>();
} 