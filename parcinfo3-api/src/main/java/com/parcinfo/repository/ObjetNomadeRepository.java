package com.parcinfo.repository;

import com.parcinfo.model.ObjetNomade;
import com.parcinfo.model.TypeObjetNomade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObjetNomadeRepository extends JpaRepository<ObjetNomade, Long> {
    Optional<ObjetNomade> findByNumeroSerie(String numeroSerie);
    List<ObjetNomade> findByType(TypeObjetNomade type);
    List<ObjetNomade> findByMarque(String marque);
    List<ObjetNomade> findByEstActif(boolean estActif);
    List<ObjetNomade> findByUtilisateurIdPersonne(Long utilisateurId);
} 