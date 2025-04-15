package com.parfinfo.repository;

import com.parfinfo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    long countByEnabled(boolean enabled);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :date")
    long countByCreatedAtAfter(@Param("date") LocalDateTime date);
    
    @Query("SELECT r.name as role, COUNT(u) as count FROM User u JOIN u.roles r GROUP BY r.name")
    Map<String, Long> countByRole();
    
    @Query("SELECT u.departement as departement, COUNT(u) as count FROM User u GROUP BY u.departement")
    Map<String, Long> countByDepartement();
    
    @Query("SELECT u.statut as statut, COUNT(u) as count FROM User u GROUP BY u.statut")
    Map<String, Long> countByStatut();

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);
} 