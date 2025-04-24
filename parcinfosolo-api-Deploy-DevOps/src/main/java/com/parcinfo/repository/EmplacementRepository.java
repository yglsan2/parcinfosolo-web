package com.parcinfo.repository;

import com.parcinfo.model.Emplacement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmplacementRepository extends JpaRepository<Emplacement, Long> {
    List<Emplacement> findByParcIdParc(Long parcId);
    List<Emplacement> findByTypeEmplacementIdTypeEmplacement(Long typeEmplacementId);
} 