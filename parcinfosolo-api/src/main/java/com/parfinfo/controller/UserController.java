package com.parfinfo.controller;

import com.parfinfo.dto.user.*;
import com.parfinfo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    public UserResponse getUserById(Long id) {
        return userService.getUserById(id);
    }

    public UserResponse createUser(CreateUserRequest request) {
        return userService.createUser(request);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }

    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
} 