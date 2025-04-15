package com.parfinfo.service;

import com.parfinfo.model.Personne;
import com.parfinfo.repository.PersonneRepository;
import com.parfinfo.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonneService {
    
    private final PersonneRepository personneRepository;
    
    public List<Personne> findAll() {
        try {
            return personneRepository.findAll();
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération des personnes")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("PERSONNE_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }
    
    public Optional<Personne> findById(Long id) {
        try {
            return personneRepository.findById(id);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération de la personne")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("PERSONNE_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }
    
    public Personne save(Personne personne) {
        try {
            // Validation des champs obligatoires
            if (personne.getNom() == null || personne.getNom().trim().isEmpty()) {
                throw new ApiException.Builder()
                    .message("Le nom est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_NOM")
                    .build();
            }
            if (personne.getPrenom() == null || personne.getPrenom().trim().isEmpty()) {
                throw new ApiException.Builder()
                    .message("Le prénom est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_PRENOM")
                    .build();
            }
            if (personne.getEmail() == null || personne.getEmail().trim().isEmpty()) {
                throw new ApiException.Builder()
                    .message("L'email est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_EMAIL")
                    .build();
            }
            if (!personne.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new ApiException.Builder()
                    .message("L'email n'est pas valide")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_EMAIL_FORMAT")
                    .build();
            }

            // Vérifier si l'email existe déjà
            if (personne.getIdPersonne() == null) {
                if (personneRepository.existsByEmail(personne.getEmail())) {
                    throw new ApiException.Builder()
                        .message("Une personne avec cet email existe déjà")
                        .status(HttpStatus.CONFLICT)
                        .code("EMAIL_EXISTS")
                        .build();
                }
            } else {
                Optional<Personne> existingPersonne = personneRepository.findByEmail(personne.getEmail());
                if (existingPersonne.isPresent() && !existingPersonne.get().getIdPersonne().equals(personne.getIdPersonne())) {
                    throw new ApiException.Builder()
                        .message("Une personne avec cet email existe déjà")
                        .status(HttpStatus.CONFLICT)
                        .code("EMAIL_EXISTS")
                        .build();
                }
            }
            return personneRepository.save(personne);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la sauvegarde de la personne")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("PERSONNE_SAVE_ERROR")
                .details(e.getMessage())
                .build();
        }
    }
    
    public void deleteById(Long id) {
        try {
            if (!personneRepository.existsById(id)) {
                throw new ApiException.Builder()
                    .message("Personne non trouvée")
                    .status(HttpStatus.NOT_FOUND)
                    .code("PERSONNE_NOT_FOUND")
                    .build();
            }
            personneRepository.deleteById(id);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la suppression de la personne")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("PERSONNE_DELETE_ERROR")
                .details(e.getMessage())
                .build();
        }
    }
    
    public long count() {
        try {
            return personneRepository.count();
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors du comptage des personnes")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("PERSONNE_COUNT_ERROR")
                .details(e.getMessage())
                .build();
        }
    }
} 