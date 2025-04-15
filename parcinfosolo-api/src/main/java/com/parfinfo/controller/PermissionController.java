package com.parfinfo.controller;

import com.parfinfo.dto.permission.PermissionDTO;
import com.parfinfo.service.PermissionService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    public List<PermissionDTO> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    public PermissionDTO getPermissionById(Long id) {
        return permissionService.getPermissionById(id)
                .orElse(null);
    }

    public PermissionDTO getPermissionByName(String name) {
        return permissionService.getPermissionByName(name)
                .orElse(null);
    }
} 