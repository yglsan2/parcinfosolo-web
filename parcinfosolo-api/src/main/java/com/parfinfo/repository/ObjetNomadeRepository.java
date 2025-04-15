package com.parfinfo.repository;

import com.parfinfo.model.ObjetNomade;
import com.parfinfo.model.TypeObjetNomade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjetNomadeRepository extends JpaRepository<ObjetNomade, Long> {
    List<ObjetNomade> findByType(TypeObjetNomade type);
    List<ObjetNomade> findByStatut(String statut);

    @Query("SELECT o FROM ObjetNomade o WHERE " +
           "(:type IS NULL OR o.type = :type) AND " +
           "(:statut IS NULL OR o.statut = :statut) AND " +
           "(:marque IS NULL OR o.marque LIKE %:marque%) AND " +
           "(:modele IS NULL OR o.modele LIKE %:modele%)")
    List<ObjetNomade> searchObjetsNomades(
        @Param("type") TypeObjetNomade type,
        @Param("statut") String statut,
        @Param("marque") String marque,
        @Param("modele") String modele
    );
} 