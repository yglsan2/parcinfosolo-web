package com.parcinfo.web.repository;

import com.parcinfo.web.model.ObjetNomade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObjetNomadeRepository extends JpaRepository<ObjetNomade, Long> {
    Optional<ObjetNomade> findByNumeroSerie(String numeroSerie);
    List<ObjetNomade> findByType(String type);
    List<ObjetNomade> findByMarque(String marque);
    List<ObjetNomade> findByEtat(String etat);
    List<ObjetNomade> findByPersonneIdPersonne(Long personneId);
} 