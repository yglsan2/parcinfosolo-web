package com.parfinfo.service;

import com.parfinfo.model.Notification;
import com.parfinfo.repository.NotificationRepository;
import com.parfinfo.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> findAll() {
        try {
            return notificationRepository.findAll();
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération des notifications")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("NOTIFICATION_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public Optional<Notification> findById(Long id) {
        try {
            return notificationRepository.findById(id);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération de la notification")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("NOTIFICATION_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public List<Notification> findByUserId(Long userId) {
        try {
            return notificationRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération des notifications de l'utilisateur")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("NOTIFICATION_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public List<Notification> findUnreadByUserId(Long userId) {
        try {
            return notificationRepository.findByUserIdAndLuFalse(userId);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la récupération des notifications non lues")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("NOTIFICATION_FETCH_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public Notification save(Notification notification) {
        try {
            // Validation des champs obligatoires
            if (notification.getType() == null) {
                throw new ApiException.Builder()
                    .message("Le type de notification est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_TYPE")
                    .build();
            }
            if (notification.getMessage() == null || notification.getMessage().trim().isEmpty()) {
                throw new ApiException.Builder()
                    .message("Le message est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_MESSAGE")
                    .build();
            }
            if (notification.getUser() == null) {
                throw new ApiException.Builder()
                    .message("L'utilisateur est obligatoire")
                    .status(HttpStatus.BAD_REQUEST)
                    .code("INVALID_USER")
                    .build();
            }

            return notificationRepository.save(notification);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la sauvegarde de la notification")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("NOTIFICATION_SAVE_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public Notification markAsRead(Long id) {
        try {
            Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ApiException.Builder()
                    .message("Notification non trouvée")
                    .status(HttpStatus.NOT_FOUND)
                    .code("NOTIFICATION_NOT_FOUND")
                    .build());

            notification.setLu(true);
            return notificationRepository.save(notification);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors du marquage de la notification comme lue")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("NOTIFICATION_UPDATE_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public void deleteById(Long id) {
        try {
            if (!notificationRepository.existsById(id)) {
                throw new ApiException.Builder()
                    .message("Notification non trouvée")
                    .status(HttpStatus.NOT_FOUND)
                    .code("NOTIFICATION_NOT_FOUND")
                    .build();
            }
            notificationRepository.deleteById(id);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la suppression de la notification")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("NOTIFICATION_DELETE_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    public void deleteAllByUserId(Long userId) {
        try {
            notificationRepository.deleteByUserId(userId);
        } catch (Exception e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la suppression des notifications de l'utilisateur")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("NOTIFICATION_DELETE_ERROR")
                .details(e.getMessage())
                .build();
        }
    }
} 