package com.parfinfo.controller;

import com.parfinfo.dto.notification.*;
import com.parfinfo.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "API de gestion des notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @Operation(summary = "Lister toutes les notifications")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des notifications récupérée avec succès"),
        @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    public ResponseEntity<Page<NotificationResponse>> getAllNotifications(Pageable pageable) {
        return ResponseEntity.ok(notificationService.getAllNotifications(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une notification par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notification trouvée"),
        @ApiResponse(responseCode = "404", description = "Notification non trouvée")
    })
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle notification")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notification créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<NotificationResponse> createNotification(@Valid @RequestBody CreateNotificationRequest request) {
        return ResponseEntity.ok(notificationService.createNotification(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une notification")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notification mise à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Notification non trouvée")
    })
    public ResponseEntity<NotificationResponse> updateNotification(
            @PathVariable Long id,
            @Valid @RequestBody UpdateNotificationRequest request) {
        return ResponseEntity.ok(notificationService.updateNotification(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une notification")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notification supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Notification non trouvée")
    })
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtenir les notifications d'un utilisateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notifications récupérées avec succès"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<List<NotificationResponse>> getNotificationsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "Marquer une notification comme lue")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notification marquée comme lue avec succès"),
        @ApiResponse(responseCode = "404", description = "Notification non trouvée")
    })
    public ResponseEntity<NotificationResponse> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @PutMapping("/user/{userId}/read-all")
    @Operation(summary = "Marquer toutes les notifications d'un utilisateur comme lues")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notifications marquées comme lues avec succès"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<?> markAllAsRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }
} 