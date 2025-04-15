package com.parfinfo.controller;

import com.parfinfo.dto.role.*;
import com.parfinfo.service.RoleService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    public List<RoleResponse> getAllRoles() {
        return roleService.getAllRoles();
    }

    public RoleResponse getRoleById(Long id) {
        return roleService.getRoleById(id);
    }

    public RoleResponse createRole(CreateRoleRequest request) {
        return roleService.createRole(request);
    }

    public RoleResponse updateRole(Long id, UpdateRoleRequest request) {
        return roleService.updateRole(id, request);
    }

    public void deleteRole(Long id) {
        roleService.deleteRole(id);
    }
} 