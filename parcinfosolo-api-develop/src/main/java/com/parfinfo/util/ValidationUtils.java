package com.parfinfo.util;

import com.parfinfo.exception.CookiePreferencesException;

public class ValidationUtils {

    public static void validateUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new CookiePreferencesException("L'identifiant utilisateur ne peut pas être vide");
        }
        
        if (userId.length() > 50) {
            throw new CookiePreferencesException("L'identifiant utilisateur ne peut pas dépasser 50 caractères");
        }
    }
    
    public static void validateCookiePreferences(boolean analytics, boolean marketing, 
                                               boolean functional, boolean necessary) {
        if (!necessary) {
            throw new CookiePreferencesException("Les cookies nécessaires doivent être activés");
        }
    }
} 