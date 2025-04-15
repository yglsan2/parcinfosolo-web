package com.parfinfo.repository;

import com.parfinfo.model.AppareilStandard;
import com.parfinfo.model.TypeAppareil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppareilStandardRepository extends JpaRepository<AppareilStandard, Long> {
    List<AppareilStandard> findByType(TypeAppareil type);
    Optional<AppareilStandard> findByNumeroSerie(String numeroSerie);
    boolean existsByNumeroSerie(String numeroSerie);
} 