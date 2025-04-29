package com.parcinfo.web.repository;

import com.parcinfo.web.model.Appareil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppareilRepository extends JpaRepository<Appareil, Long> {
} 