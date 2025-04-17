package com.parcinfo.web.repository;

import com.parcinfo.web.model.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {
    List<Intervention> findByPeripheriqueIdOrderByDateDesc(Long peripheriqueId);
} 