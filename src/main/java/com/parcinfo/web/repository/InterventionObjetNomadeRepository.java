package com.parcinfo.web.repository;

import com.parcinfo.web.model.InterventionObjetNomade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterventionObjetNomadeRepository extends JpaRepository<InterventionObjetNomade, Long> {
    List<InterventionObjetNomade> findByObjetNomadeId(Long objetNomadeId);
} 