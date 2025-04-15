package com.parfinfo.repository;

import com.parfinfo.entity.EtatEquipement;
import com.parfinfo.entity.ObjetNomade;
import com.parfinfo.entity.TypeObjetNomade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<ObjetNomade> searchObjetsNomades(
        @Param("type") TypeObjetNomade type,
        @Param("statut") EtatEquipement statut,
        @Param("marque") String marque,
        @Param("modele") String modele,
        Pageable pageable
    );

    @Query("SELECT DISTINCT o.type FROM ObjetNomade o")
    List<String> findAllTypes();

    @Query("SELECT DISTINCT o.statut FROM ObjetNomade o")
    List<String> findAllStatuts();
} 