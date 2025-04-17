package com.parcinfo.web.repository;

import com.parcinfo.web.model.Peripherique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeripheriqueRepository extends JpaRepository<Peripherique, Long> {
} 