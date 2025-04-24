package com.parcinfo.web.repository;

import com.parcinfo.web.model.Ordinateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdinateurRepository extends JpaRepository<Ordinateur, Long> {
} 