package com.parcinfo.repository;

import com.parcinfo.model.Ordinateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdinateurRepository extends JpaRepository<Ordinateur, Long> {
} 