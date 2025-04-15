package com.parfinfo.repository;

import com.parfinfo.entity.Appareil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppareilRepository extends JpaRepository<Appareil, Long> {
    
    List<Appareil> findByTypeAndStatus(String type, String status);
    
    @Query("SELECT DISTINCT a.type FROM Appareil a")
    List<String> findAllTypes();
    
    @Query("SELECT DISTINCT a.status FROM Appareil a")
    List<String> findAllStatus();
} 