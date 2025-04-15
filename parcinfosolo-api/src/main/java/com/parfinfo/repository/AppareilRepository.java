package com.parfinfo.repository;

import com.parfinfo.model.Appareil;
import com.parfinfo.model.TypeAppareil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppareilRepository extends JpaRepository<Appareil, Long> {
    List<Appareil> findByType(TypeAppareil type);
    List<Appareil> findTop5ByOrderByIdAppareilDesc();
    long countByType(String type);
} 