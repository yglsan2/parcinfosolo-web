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
@Table(name = "objet_nomade")
public class ObjetNomade extends Appareil {
    @Column(name = "type_objet_nomade")
    @Enumerated(EnumType.STRING)
    private TypeObjetNomade typeSpecifique;

    @JsonManagedReference("objet-nomade-peripheriques")
    @OneToMany(mappedBy = "objetNomade", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Peripherique> peripheriques = new HashSet<>();
} 