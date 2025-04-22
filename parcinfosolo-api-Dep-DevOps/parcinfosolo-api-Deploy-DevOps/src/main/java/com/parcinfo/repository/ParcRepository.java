package com.parcinfo.repository;

import com.parcinfo.model.Parc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcRepository extends JpaRepository<Parc, Long> {
} 