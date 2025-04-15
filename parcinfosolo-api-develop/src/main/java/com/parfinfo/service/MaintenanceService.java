package com.parfinfo.service;

import com.parfinfo.model.Maintenance;
import com.parfinfo.repository.MaintenanceRepository;
import com.parfinfo.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final AppareilService appareilService;
    private final PeripheriqueService peripheriqueService;

    public MaintenanceService(
        MaintenanceRepository maintenanceRepository,
        AppareilService appareilService,
        PeripheriqueService peripheriqueService
    ) {
        this.maintenanceRepository = maintenanceRepository;
        this.appareilService = appareilService;
        this.peripheriqueService = peripheriqueService;
    }

    public List<Maintenance> findAll() {
        try {
            return maintenanceRepository.findAll();
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération des maintenances")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("MAINTENANCE_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public Optional<Maintenance> findById(Long id) {
        try {
            return maintenanceRepository.findById(id);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération de la maintenance")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("MAINTENANCE_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public List<Maintenance> findByAppareilId(Long appareilId) {
        try {
            return maintenanceRepository.findByAppareilId(appareilId);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération des maintenances de l'appareil")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("MAINTENANCE_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public List<Maintenance> findByPeripheriqueId(Long peripheriqueId) {
        try {
            return maintenanceRepository.findByPeripheriqueId(peripheriqueId);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération des maintenances du périphérique")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("MAINTENANCE_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public Maintenance save(Maintenance maintenance) {
        try {
            // Validation des champs obligatoires
            if (maintenance.getType() == null) {
                throw new ApiException.Builder()
                    .message("Le type de maintenance est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_TYPE")
                    .build();
            }
            if (maintenance.getDateMaintenance() == null) {
                throw new ApiException.Builder()
                    .message("La date de maintenance est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_DATE")
                    .build();
            }
            if (maintenance.getDescription() == null || maintenance.getDescription().trim().isEmpty()) {
                throw new ApiException.Builder()
                    .message("La description est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_DESCRIPTION")
                    .build();
            }

            // Vérification de l'existence de l'appareil ou du périphérique
            if (maintenance.getAppareil() != null) {
                appareilService.findById(maintenance.getAppareil().getId())
                    .orElseThrow(() -> new ApiException.Builder()
                        .message("Appareil non trouvé")
                        .status(HttpStatus.NOT_FOUND)
                        .code("APPAREIL_NOT_FOUND")
                        .build());
            }
            if (maintenance.getPeripherique() != null) {
                peripheriqueService.findById(maintenance.getPeripherique().getId())
                    .orElseThrow(() -> new ApiException.Builder()
                        .message("Périphérique non trouvé")
                        .status(HttpStatus.NOT_FOUND)
                        .code("PERIPHERIQUE_NOT_FOUND")
                        .build());
            }

            return maintenanceRepository.save(maintenance);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la sauvegarde de la maintenance")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("MAINTENANCE_SAVE_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public void deleteById(Long id) {
        try {
            if (!maintenanceRepository.existsById(id)) {
                throw new ApiException.Builder()
                    .message("Maintenance non trouvée")
                    .status(HttpStatus.NOT_FOUND)
                    .code("MAINTENANCE_NOT_FOUND")
                    .build();
            }
            maintenanceRepository.deleteById(id);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la suppression de la maintenance")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("MAINTENANCE_DELETE_ERROR")
                .details(e.getMessage())
                .build();
        }
    }
} 