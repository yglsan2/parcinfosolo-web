package com.parcinfo.repository;

import com.parcinfo.model.Affectation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AffectationRepository extends JpaRepository<Affectation, Long> {
    List<Affectation> findByPersonneId(Long personneId);
    List<Affectation> findByAppareilId(Long appareilId);
} 