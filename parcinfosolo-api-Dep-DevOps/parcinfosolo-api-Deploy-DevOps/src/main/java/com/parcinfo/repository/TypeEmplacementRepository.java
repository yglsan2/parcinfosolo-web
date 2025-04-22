package com.parcinfo.repository;

import com.parcinfo.model.TypeEmplacement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeEmplacementRepository extends JpaRepository<TypeEmplacement, Long> {
} 