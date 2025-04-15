package com.parfinfo.repository;

import com.parfinfo.entity.Activite;
import com.parfinfo.entity.Utilisateur;
import com.parfinfo.entity.Appareil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActiviteRepository extends JpaRepository<Activite, Long> {
    
    List<Activite> findByUtilisateur(Utilisateur utilisateur);
    
    Page<Activite> findByUtilisateur(Utilisateur utilisateur, Pageable pageable);
    
    Page<Activite> findByAppareil(Appareil appareil, Pageable pageable);
    
    List<Activite> findByType(String type);
    
    List<Activite> findByDateCreationBetween(LocalDateTime debut, LocalDateTime fin);
    
    @Query("SELECT DISTINCT a.type FROM Activite a")
    List<String> findAllTypes();
    
    @Query("SELECT a FROM Activite a WHERE " +
           "(:type IS NULL OR a.type = :type) AND " +
           "(:utilisateurId IS NULL OR a.utilisateur.id = :utilisateurId) AND " +
           "(:debut IS NULL OR a.dateCreation >= :debut) AND " +
           "(:fin IS NULL OR a.dateCreation <= :fin)")
    Page<Activite> searchActivites(
            @Param("type") String type,
            @Param("utilisateurId") Long utilisateurId,
            @Param("debut") LocalDateTime debut,
            @Param("fin") LocalDateTime fin,
            Pageable pageable);
} 