package com.parcinfo.web.repository;

import com.parcinfo.web.model.Peripherique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour la gestion des périphériques.
 * Fournit les opérations CRUD de base et des méthodes de recherche personnalisées.
 *
 * @author [Votre nom]
 * @version 1.0
 */
@Repository
public interface PeripheriqueRepository extends JpaRepository<Peripherique, Long> {
    // Les méthodes de base de JpaRepository sont automatiquement disponibles :
    // findAll(), findById(), save(), deleteById(), etc.
} 