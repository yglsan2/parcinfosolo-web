package com.parfinfo.security;

import de.mkammerer.argon2.Argon2;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class CustomArgon2PasswordEncoder implements PasswordEncoder {

    private final Argon2 argon2;
    private final String pepper;
    private final SecureRandom secureRandom;
    
    // Paramètres Argon2 recommandés
    private static final int ITERATIONS = 10;
    private static final int MEMORY = 65536;
    private static final int PARALLELISM = 1;

    public CustomArgon2PasswordEncoder(Argon2 argon2, String pepper) {
        this.argon2 = argon2;
        this.pepper = pepper;
        this.secureRandom = new SecureRandom();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        byte[] salt = new byte[32];
        secureRandom.nextBytes(salt);
        
        // Ajout du pepper au mot de passe
        char[] pepperedPassword = (rawPassword.toString() + pepper).toCharArray();
        
        try {
            // Hachage avec Argon2id
            String hash = argon2.hash(ITERATIONS, MEMORY, PARALLELISM, pepperedPassword);
                
            // Combine le sel et le hash pour le stockage
            String result = Base64.getEncoder().encodeToString(salt) + "$" + hash;
            
            // Nettoyage sécurisé du mot de passe en mémoire
            java.util.Arrays.fill(pepperedPassword, '\0');
            
            return result;
        } catch (Exception e) {
            // Nettoyage en cas d'erreur
            java.util.Arrays.fill(pepperedPassword, '\0');
            throw new RuntimeException("Erreur lors du hachage du mot de passe", e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String[] parts = encodedPassword.split("\\$");
        if (parts.length != 2) {
            return false;
        }

        char[] pepperedPassword = (rawPassword.toString() + pepper).toCharArray();
        
        try {
            // Vérification avec Argon2id
            boolean matches = argon2.verify(parts[1], pepperedPassword);
            
            // Nettoyage sécurisé du mot de passe en mémoire
            java.util.Arrays.fill(pepperedPassword, '\0');
            
            return matches;
        } catch (Exception e) {
            // Nettoyage en cas d'erreur
            java.util.Arrays.fill(pepperedPassword, '\0');
            return false;
        }
    }
} 