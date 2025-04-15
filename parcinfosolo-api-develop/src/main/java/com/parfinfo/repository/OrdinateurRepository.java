package com.parfinfo.repository;

import com.parfinfo.model.Ordinateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdinateurRepository extends JpaRepository<Ordinateur, Long> {
    List<Ordinateur> findByType(String type);
    List<Ordinateur> findByStatut(String statut);

    @Query("SELECT o FROM Ordinateur o WHERE " +
           "(:type IS NULL OR o.type = :type) AND " +
           "(:statut IS NULL OR o.statut = :statut) AND " +
           "(:marque IS NULL OR o.marque LIKE %:marque%) AND " +
           "(:modele IS NULL OR o.modele LIKE %:modele%)")
    List<Ordinateur> searchOrdinateurs(
        @Param("type") String type,
        @Param("statut") String statut,
        @Param("marque") String marque,
        @Param("modele") String modele
    );
} 