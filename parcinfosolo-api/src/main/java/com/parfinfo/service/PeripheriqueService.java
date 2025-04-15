package com.parfinfo.service;

import com.parfinfo.model.Peripherique;
import com.parfinfo.repository.PeripheriqueRepository;
import com.parfinfo.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeripheriqueService {
    private final PeripheriqueRepository peripheriqueRepository;

    public PeripheriqueService(PeripheriqueRepository peripheriqueRepository) {
        this.peripheriqueRepository = peripheriqueRepository;
    }

    public List<Peripherique> findAll() {
        try {
            return peripheriqueRepository.findAll();
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération des périphériques")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("PERIPHERIQUE_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }
    
    public List<Peripherique> getAllPeripheriques() {
        return findAll();
    }
    
    public Optional<Peripherique> findById(Long id) {
        try {
            return peripheriqueRepository.findById(id);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération du périphérique")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("PERIPHERIQUE_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public Optional<Peripherique> getPeripheriqueById(Long id) {
        return findById(id);
    }
    
    public Peripherique save(Peripherique peripherique) {
        try {
            // Validation des champs obligatoires
            if (peripherique.getType() == null || peripherique.getType().trim().isEmpty()) {
                throw new ApiException.Builder()
                    .message("Le type est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_TYPE")
                    .build();
            }
            if (peripherique.getMarque() == null || peripherique.getMarque().trim().isEmpty()) {
                throw new ApiException.Builder()
                    .message("La marque est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_MARQUE")
                    .build();
            }
            if (peripherique.getModele() == null || peripherique.getModele().trim().isEmpty()) {
                throw new ApiException.Builder()
                    .message("Le modèle est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_MODELE")
                    .build();
            }
            if (peripherique.getStatut() == null || peripherique.getStatut().trim().isEmpty()) {
                throw new ApiException.Builder()
                    .message("Le statut est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_STATUT")
                    .build();
            }

            return peripheriqueRepository.save(peripherique);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la sauvegarde du périphérique")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("PERIPHERIQUE_SAVE_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public Peripherique createPeripherique(Peripherique peripherique) {
        return save(peripherique);
    }

    public Peripherique updatePeripherique(Long id, Peripherique peripherique) {
        try {
            if (!peripheriqueRepository.existsById(id)) {
                throw new ApiException.Builder()
                    .message("Périphérique non trouvé")
                    .status(HttpStatus.NOT_FOUND)
                    .code("PERIPHERIQUE_NOT_FOUND")
                    .build();
            }
            peripherique.setId(id);
            return save(peripherique);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la mise à jour du périphérique")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("PERIPHERIQUE_UPDATE_ERROR")
                .details(e.getMessage())
                .build();
        }
    }
    
    public void deleteById(Long id) {
        try {
            if (!peripheriqueRepository.existsById(id)) {
                throw new ApiException.Builder()
                    .message("Périphérique non trouvé")
                    .status(HttpStatus.NOT_FOUND)
                    .code("PERIPHERIQUE_NOT_FOUND")
                    .build();
            }
            peripheriqueRepository.deleteById(id);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la suppression du périphérique")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("PERIPHERIQUE_DELETE_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public void deletePeripherique(Long id) {
        deleteById(id);
    }

    public List<Peripherique> getPeripheriquesByType(String type) {
        try {
            return peripheriqueRepository.findByType(type);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération des périphériques par type")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("PERIPHERIQUE_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public List<Peripherique> getPeripheriquesByStatut(String statut) {
        try {
            return peripheriqueRepository.findByStatut(statut);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération des périphériques par statut")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("PERIPHERIQUE_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public List<Peripherique> searchPeripheriques(String type, String statut, String marque, String modele) {
        try {
            return peripheriqueRepository.searchPeripheriques(type, statut, marque, modele);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la recherche des périphériques")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("PERIPHERIQUE_SEARCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }
} 