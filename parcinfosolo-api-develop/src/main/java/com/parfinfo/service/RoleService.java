package com.parfinfo.service;

import com.parfinfo.dto.RoleDTO;
import com.parfinfo.entity.Role;
import com.parfinfo.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<RoleDTO> getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(this::convertToDTO);
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = convertToEntity(roleDTO);
        Role savedRole = roleRepository.save(role);
        return convertToDTO(savedRole);
    }

    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role non trouvé avec l'id : " + id);
        }
        Role role = convertToEntity(roleDTO);
        role.setId(id);
        Role updatedRole = roleRepository.save(role);
        return convertToDTO(updatedRole);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    private RoleDTO convertToDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setNom(role.getNom());
        dto.setDescription(role.getDescription());
        // TODO: Convertir les permissions si nécessaire
        return dto;
    }

    private Role convertToEntity(RoleDTO dto) {
        Role role = new Role();
        role.setId(dto.getId());
        role.setNom(dto.getNom());
        role.setDescription(dto.getDescription());
        // TODO: Convertir les permissions si nécessaire
        return role;
    }
} 