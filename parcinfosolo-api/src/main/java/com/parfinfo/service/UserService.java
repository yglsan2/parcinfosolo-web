package com.parfinfo.service;

import com.parfinfo.dto.user.CreateUserRequest;
import com.parfinfo.dto.user.UpdateUserRequest;
import com.parfinfo.dto.user.UserResponse;
import com.parfinfo.entity.User;
import com.parfinfo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        User user = new User();
        updateUserFromRequest(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActif(true);
        user.setDateCreation(LocalDateTime.now());
        return mapToResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        updateUserFromRequest(user, request);
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setDateModification(LocalDateTime.now());
        return mapToResponse(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private void updateUserFromRequest(User user, CreateUserRequest request) {
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setTelephone(request.getTelephone());
        user.setService(request.getService());
        user.setPoste(request.getPoste());
    }

    private void updateUserFromRequest(User user, UpdateUserRequest request) {
        if (request.getUsername() != null) user.setUsername(request.getUsername());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getNom() != null) user.setNom(request.getNom());
        if (request.getPrenom() != null) user.setPrenom(request.getPrenom());
        if (request.getTelephone() != null) user.setTelephone(request.getTelephone());
        if (request.getService() != null) user.setService(request.getService());
        if (request.getPoste() != null) user.setPoste(request.getPoste());
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setNom(user.getNom());
        response.setPrenom(user.getPrenom());
        response.setTelephone(user.getTelephone());
        response.setService(user.getService());
        response.setPoste(user.getPoste());
        response.setRoles(user.getRoles().stream()
                .map(role -> role.getName())
                .collect(java.util.stream.Collectors.toSet()));
        response.setDateCreation(user.getDateCreation());
        response.setDateModification(user.getDateModification());
        response.setDerniereConnexion(user.getDerniereConnexion());
        response.setActif(user.isActif());
        return response;
    }
} 