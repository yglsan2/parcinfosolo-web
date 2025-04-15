package com.parfinfo.repository;

import com.parfinfo.model.Peripherique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeripheriqueRepository extends JpaRepository<Peripherique, Long> {
    List<Peripherique> findByType(String type);
    List<Peripherique> findByStatut(String statut);

    @Query("SELECT p FROM Peripherique p WHERE " +
           "(:type IS NULL OR p.type = :type) AND " +
           "(:statut IS NULL OR p.statut = :statut) AND " +
           "(:marque IS NULL OR p.marque LIKE %:marque%) AND " +
           "(:modele IS NULL OR p.modele LIKE %:modele%)")
    List<Peripherique> searchPeripheriques(
        @Param("type") String type,
        @Param("statut") String statut,
        @Param("marque") String marque,
        @Param("modele") String modele
    );
} 