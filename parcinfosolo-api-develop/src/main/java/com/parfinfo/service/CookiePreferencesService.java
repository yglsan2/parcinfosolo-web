package com.parfinfo.service;

import com.parfinfo.dto.CookiePreferencesDTO;
import com.parfinfo.exception.CookiePreferencesException;
import com.parfinfo.model.CookiePreferences;
import com.parfinfo.repository.CookiePreferencesRepository;
import com.parfinfo.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class CookiePreferencesService {

    @Autowired
    private CookiePreferencesRepository cookiePreferencesRepository;

    public CookiePreferencesDTO savePreferences(String userId, boolean analytics, boolean marketing, 
                                          boolean functional, boolean necessary) {
        try {
            ValidationUtils.validateUserId(userId);
            ValidationUtils.validateCookiePreferences(analytics, marketing, functional, necessary);
            
            CookiePreferences preferences = new CookiePreferences();
            preferences.setUserId(userId);
            preferences.setAnalytics(analytics);
            preferences.setMarketing(marketing);
            preferences.setFunctional(functional);
            preferences.setNecessary(necessary);
            preferences.setLastUpdated(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            
            CookiePreferences savedPreferences = cookiePreferencesRepository.save(preferences);
            return CookiePreferencesDTO.fromEntity(savedPreferences);
        } catch (Exception e) {
            throw new CookiePreferencesException("Erreur lors de la sauvegarde des préférences de cookies", e);
        }
    }

    public CookiePreferencesDTO getPreferences(String userId) {
        try {
            ValidationUtils.validateUserId(userId);
            
            CookiePreferences preferences = cookiePreferencesRepository.findById(userId)
                    .orElseGet(() -> {
                        CookiePreferences defaultPreferences = new CookiePreferences();
                        defaultPreferences.setUserId(userId);
                        defaultPreferences.setAnalytics(false);
                        defaultPreferences.setMarketing(false);
                        defaultPreferences.setFunctional(false);
                        defaultPreferences.setNecessary(true);
                        defaultPreferences.setLastUpdated(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                        return defaultPreferences;
                    });
            
            return CookiePreferencesDTO.fromEntity(preferences);
        } catch (Exception e) {
            throw new CookiePreferencesException("Erreur lors de la récupération des préférences de cookies", e);
        }
    }
} 