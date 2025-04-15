package com.parfinfo.security;

import com.parfinfo.entity.Role;
import com.parfinfo.entity.Utilisateur;
import com.parfinfo.exception.ResourceConflictException;
import com.parfinfo.exception.ResourceNotFoundException;
import com.parfinfo.repository.RoleRepository;
import com.parfinfo.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + email));

        List<String> roles = new ArrayList<>();
        utilisateur.getRoles().forEach(role -> roles.add(role.getName()));

        return User.builder()
                .username(utilisateur.getEmail())
                .password(utilisateur.getPassword())
                .roles(roles.toArray(new String[0]))
                .build();
    }

    @Transactional
    public Utilisateur createUser(Utilisateur utilisateur) {
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            throw new ResourceConflictException("Utilisateur", "email", utilisateur.getEmail());
        }

        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        return utilisateurRepository.save(utilisateur);
    }

    @Transactional
    public Utilisateur updateUser(Long id, Utilisateur utilisateurDetails) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));

        utilisateur.setNom(utilisateurDetails.getNom());
        utilisateur.setPrenom(utilisateurDetails.getPrenom());
        utilisateur.setEmail(utilisateurDetails.getEmail());
        
        if (utilisateurDetails.getPassword() != null && !utilisateurDetails.getPassword().isEmpty()) {
            utilisateur.setPassword(passwordEncoder.encode(utilisateurDetails.getPassword()));
        }

        return utilisateurRepository.save(utilisateur);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilisateur", "id", id);
        }
        utilisateurRepository.deleteById(id);
    }

    public Utilisateur getUserById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
    }

    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    @Transactional
    public Utilisateur addRoleToUser(Long userId, Long roleId) {
        Utilisateur utilisateur = getUserById(userId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));

        utilisateur.getRoles().add(role);
        return utilisateurRepository.save(utilisateur);
    }

    @Transactional
    public Utilisateur removeRoleFromUser(Long userId, Long roleId) {
        Utilisateur utilisateur = getUserById(userId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));

        utilisateur.getRoles().remove(role);
        return utilisateurRepository.save(utilisateur);
    }
} 