package com.parfinfo.service;

import com.parfinfo.dto.PermissionDTO;
import com.parfinfo.entity.Permission;
import com.parfinfo.repository.PermissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public List<PermissionDTO> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PermissionDTO> getPermissionById(Long id) {
        return permissionRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Optional<PermissionDTO> getPermissionByName(String name) {
        return permissionRepository.findByName(name)
                .map(this::convertToDTO);
    }

    public PermissionDTO createPermission(PermissionDTO permissionDTO) {
        Permission permission = convertToEntity(permissionDTO);
        Permission savedPermission = permissionRepository.save(permission);
        return convertToDTO(savedPermission);
    }

    public Optional<PermissionDTO> updatePermission(Long id, PermissionDTO permissionDTO) {
        return permissionRepository.findById(id)
                .map(existingPermission -> {
                    existingPermission.setName(permissionDTO.getName());
                    existingPermission.setDescription(permissionDTO.getDescription());
                    existingPermission.setResource(permissionDTO.getResource());
                    existingPermission.setAction(permissionDTO.getAction());
                    return convertToDTO(permissionRepository.save(existingPermission));
                });
    }

    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }

    private PermissionDTO convertToDTO(Permission permission) {
        return new PermissionDTO(
                permission.getId(),
                permission.getName(),
                permission.getDescription(),
                permission.getResource(),
                permission.getAction()
        );
    }

    private Permission convertToEntity(PermissionDTO dto) {
        return new Permission(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getResource(),
                dto.getAction()
        );
    }
} 