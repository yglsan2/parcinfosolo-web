package com.parfinfo.dto;

import lombok.Data;
import java.util.Set;

@Data
public class RoleDTO {
    private Long id;
    private String nom;
    private String description;
    private Set<PermissionDTO> permissions;
} 