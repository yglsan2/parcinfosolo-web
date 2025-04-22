package com.parcinfo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleType name;

    private String description;

    public Role(RoleType name) {
        this.name = name;
    }

    public Role(RoleType name, String description) {
        this.name = name;
        this.description = description;
    }
} 