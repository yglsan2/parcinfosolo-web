package com.parfinfo.controller;

import com.parfinfo.dto.user.CreateUserRequest;
import com.parfinfo.dto.user.UpdateUserRequest;
import com.parfinfo.dto.user.UserResponse;
import com.parfinfo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserResponse userResponse;
    private CreateUserRequest createRequest;
    private UpdateUserRequest updateRequest;

    @BeforeEach
    void setUp() {
        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setUsername("testUser");
        userResponse.setEmail("test@example.com");
        userResponse.setRole("USER");

        createRequest = new CreateUserRequest();
        createRequest.setUsername("newUser");
        createRequest.setPassword("password123");
        createRequest.setEmail("new@example.com");
        createRequest.setRole("USER");

        updateRequest = new UpdateUserRequest();
        updateRequest.setUsername("updatedUser");
        updateRequest.setEmail("updated@example.com");
        updateRequest.setRole("ADMIN");
    }

    @Test
    void getAllUsers_ShouldReturnPageOfUsers() {
        // Arrange
        List<UserResponse> users = Arrays.asList(userResponse);
        Page<UserResponse> page = new PageImpl<>(users);
        Pageable pageable = PageRequest.of(0, 10);
        when(userService.getAllUsers(any(Pageable.class))).thenReturn(page);

        // Act
        Page<UserResponse> result = userController.getAllUsers(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(userResponse.getUsername(), result.getContent().get(0).getUsername());
        verify(userService).getAllUsers(pageable);
    }

    @Test
    void getUserById_ShouldReturnUser() {
        // Arrange
        when(userService.getUserById(1L)).thenReturn(userResponse);

        // Act
        UserResponse result = userController.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(userResponse.getId(), result.getId());
        assertEquals(userResponse.getUsername(), result.getUsername());
        verify(userService).getUserById(1L);
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        // Arrange
        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(userResponse);

        // Act
        UserResponse result = userController.createUser(createRequest);

        // Assert
        assertNotNull(result);
        assertEquals(userResponse.getId(), result.getId());
        assertEquals(userResponse.getUsername(), result.getUsername());
        verify(userService).createUser(createRequest);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        // Arrange
        when(userService.updateUser(eq(1L), any(UpdateUserRequest.class))).thenReturn(userResponse);

        // Act
        UserResponse result = userController.updateUser(1L, updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals(userResponse.getId(), result.getId());
        assertEquals(userResponse.getUsername(), result.getUsername());
        verify(userService).updateUser(1L, updateRequest);
    }

    @Test
    void deleteUser_ShouldCallServiceDelete() {
        // Act
        userController.deleteUser(1L);

        // Assert
        verify(userService).deleteUser(1L);
    }
} 