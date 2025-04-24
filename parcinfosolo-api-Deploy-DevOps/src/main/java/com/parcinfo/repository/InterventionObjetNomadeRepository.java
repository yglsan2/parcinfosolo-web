package com.parcinfo.repository;

import com.parcinfo.model.InterventionObjetNomade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterventionObjetNomadeRepository extends JpaRepository<InterventionObjetNomade, Long> {
} 