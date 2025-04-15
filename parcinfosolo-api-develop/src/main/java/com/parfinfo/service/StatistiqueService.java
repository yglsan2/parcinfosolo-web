package com.parfinfo.service;

import com.parfinfo.dto.statistique.AppareilStatistiqueResponse;
import com.parfinfo.dto.statistique.PeripheriqueStatistiqueResponse;
import com.parfinfo.dto.statistique.UtilisateurStatistiqueResponse;
import com.parfinfo.repository.AppareilRepository;
import com.parfinfo.repository.PeripheriqueRepository;
import com.parfinfo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatistiqueService {

    private final AppareilRepository appareilRepository;
    private final PeripheriqueRepository peripheriqueRepository;
    private final UserRepository userRepository;

    public AppareilStatistiqueResponse getAppareilStatistiques() {
        return AppareilStatistiqueResponse.builder()
                .totalAppareils(appareilRepository.count())
                .appareilsEnService(appareilRepository.countByStatut("EN_SERVICE"))
                .appareilsEnPanne(appareilRepository.countByStatut("EN_PANNE"))
                .appareilsEnMaintenance(appareilRepository.countByStatut("EN_MAINTENANCE"))
                .appareilsParType(appareilRepository.countByType())
                .appareilsParMarque(appareilRepository.countByMarque())
                .appareilsParModele(appareilRepository.countByModele())
                .build();
    }

    public PeripheriqueStatistiqueResponse getPeripheriqueStatistiques() {
        return PeripheriqueStatistiqueResponse.builder()
                .totalPeripheriques(peripheriqueRepository.count())
                .peripheriquesEnService(peripheriqueRepository.countByStatut("EN_SERVICE"))
                .peripheriquesEnPanne(peripheriqueRepository.countByStatut("EN_PANNE"))
                .peripheriquesEnMaintenance(peripheriqueRepository.countByStatut("EN_MAINTENANCE"))
                .peripheriquesParType(peripheriqueRepository.countByType())
                .peripheriquesParMarque(peripheriqueRepository.countByMarque())
                .peripheriquesParModele(peripheriqueRepository.countByModele())
                .build();
    }

    public UtilisateurStatistiqueResponse getUtilisateurStatistiques() {
        return UtilisateurStatistiqueResponse.builder()
                .totalUtilisateurs(userRepository.count())
                .utilisateursActifs(userRepository.countByEnabled(true))
                .utilisateursInactifs(userRepository.countByEnabled(false))
                .nouveauxUtilisateurs(userRepository.countByCreatedAtAfter())
                .utilisateursParRole(userRepository.countByRole())
                .utilisateursParDepartement(userRepository.countByDepartement())
                .utilisateursParStatut(userRepository.countByStatut())
                .build();
    }
} 