package com.parcinfo.web.repository;

import com.parcinfo.web.model.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'accès aux données des interventions.
 */
@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {
    
    /**
     * Récupère les interventions d'un périphérique triées par date décroissante.
     */
    List<Intervention> findByPeripheriqueIdOrderByDateDesc(Long peripheriqueId);
} 