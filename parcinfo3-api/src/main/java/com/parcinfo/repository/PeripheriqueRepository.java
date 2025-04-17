package com.parcinfo.repository;

import com.parcinfo.model.Peripherique;
import com.parcinfo.model.TypePeripherique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeripheriqueRepository extends JpaRepository<Peripherique, Long> {
    Optional<Peripherique> findByNumeroSerie(String numeroSerie);
    List<Peripherique> findByType(TypePeripherique type);
    List<Peripherique> findByMarque(String marque);
    List<Peripherique> findByEstActif(boolean estActif);
    List<Peripherique> findByObjetNomadeIdObjetNomade(Long idObjetNomade);
    List<Peripherique> findByOrdinateurIdOrdinateur(Long idOrdinateur);
} 