package com.parfinfo.repository;

import com.parfinfo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Méthodes personnalisées peuvent être ajoutées ici si nécessaire
} 