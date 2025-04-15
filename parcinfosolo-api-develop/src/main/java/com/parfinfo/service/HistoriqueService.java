package com.parfinfo.service;

import com.parfinfo.model.Historique;
import com.parfinfo.repository.HistoriqueRepository;
import com.parfinfo.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HistoriqueService {
    private final HistoriqueRepository historiqueRepository;

    public HistoriqueService(HistoriqueRepository historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }

    public List<Historique> findAll() {
        try {
            return historiqueRepository.findAll();
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération de l'historique")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("HISTORIQUE_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public Optional<Historique> findById(Long id) {
        try {
            return historiqueRepository.findById(id);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération de l'entrée d'historique")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("HISTORIQUE_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public List<Historique> findByEntityTypeAndEntityId(String entityType, Long entityId) {
        try {
            return historiqueRepository.findByEntityTypeAndEntityId(entityType, entityId);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération de l'historique de l'entité")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("HISTORIQUE_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public List<Historique> findByUserId(Long userId) {
        try {
            return historiqueRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération de l'historique de l'utilisateur")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("HISTORIQUE_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public Historique save(Historique historique) {
        try {
            // Validation des champs obligatoires
            if (historique.getEntityType() == null || historique.getEntityType().trim().isEmpty()) {
                throw new ApiException.Builder()
                    .message("Le type d'entité est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_ENTITY_TYPE")
                    .build();
            }
            if (historique.getEntityId() == null) {
                throw new ApiException.Builder()
                    .message("L'ID de l'entité est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_ENTITY_ID")
                    .build();
            }
            if (historique.getAction() == null || historique.getAction().trim().isEmpty()) {
                throw new ApiException.Builder()
                    .message("L'action est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_ACTION")
                    .build();
            }
            if (historique.getUser() == null) {
                throw new ApiException.Builder()
                    .message("L'utilisateur est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_USER")
                    .build();
            }

            return historiqueRepository.save(historique);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la sauvegarde de l'entrée d'historique")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("HISTORIQUE_SAVE_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public void deleteById(Long id) {
        try {
            if (!historiqueRepository.existsById(id)) {
                throw new ApiException.Builder()
                    .message("Entrée d'historique non trouvée")
                    .status(HttpStatus.NOT_FOUND)
                    .code("HISTORIQUE_NOT_FOUND")
                    .build();
            }
            historiqueRepository.deleteById(id);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la suppression de l'entrée d'historique")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("HISTORIQUE_DELETE_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public void deleteByEntityTypeAndEntityId(String entityType, Long entityId) {
        try {
            historiqueRepository.deleteByEntityTypeAndEntityId(entityType, entityId);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la suppression de l'historique de l'entité")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("HISTORIQUE_DELETE_ERROR")
                .details(e.getMessage())
                .build();
        }
    }
} 