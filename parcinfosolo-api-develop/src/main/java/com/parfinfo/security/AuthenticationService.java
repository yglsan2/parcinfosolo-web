package com.parfinfo.security;

import com.parfinfo.config.JwtConfig;
import com.parfinfo.entity.Utilisateur;
import com.parfinfo.exception.AuthenticationException;
import com.parfinfo.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public String authenticate(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtConfig.generateToken(userDetails);
        } catch (Exception e) {
            throw new AuthenticationException("Identifiants invalides", e);
        }
    }

    public Utilisateur getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("Utilisateur non authentifié");
        }
        
        String email = authentication.getName();
        return utilisateurRepository.findByEmail(email)
            .orElseThrow(() -> new AuthenticationException("Utilisateur non trouvé"));
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }
} 