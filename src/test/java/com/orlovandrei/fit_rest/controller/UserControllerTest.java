package com.orlovandrei.fit_rest.controller;

import com.orlovandrei.fit_rest.dto.mapper.UserMapper;
import com.orlovandrei.fit_rest.dto.user.CreateUserRequest;
import com.orlovandrei.fit_rest.dto.user.UserResponse;
import com.orlovandrei.fit_rest.dto.user.UpdateUserRequest;
import com.orlovandrei.fit_rest.entity.User;
import com.orlovandrei.fit_rest.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @Test
    void getById_success() {
        User user = new User();
        UserResponse response = new UserResponse();
        Mockito.when(userService.getById(1L)).thenReturn(user);
        Mockito.when(userMapper.toDto(user)).thenReturn(response);

        ResponseEntity<UserResponse> result = userController.getById(1L);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(response, result.getBody());
    }

    @Test
    void getByUsername_success() {
        User user = new User();
        UserResponse response = new UserResponse();
        Mockito.when(userService.getByUsername("username")).thenReturn(user);
        Mockito.when(userMapper.toDto(user)).thenReturn(response);

        ResponseEntity<UserResponse> result = userController.getByUsername("username");
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(response, result.getBody());
    }

    @Test
    void getByEmail_success() {
        User user = new User();
        UserResponse response = new UserResponse();
        Mockito.when(userService.getByEmail("mail@mail.com")).thenReturn(user);
        Mockito.when(userMapper.toDto(user)).thenReturn(response);

        ResponseEntity<UserResponse> result = userController.getByEmail("mail@mail.com");
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(response, result.getBody());
    }

    @Test
    void getAll_success() {
        User user = new User();
        UserResponse response = new UserResponse();
        Page<User> userPage = new PageImpl<>(List.of(user));
        Page<UserResponse> responsePage = new PageImpl<>(List.of(response));
        Mockito.when(userService.findAll(any(PageRequest.class))).thenReturn(userPage);
        Mockito.when(userMapper.toDto(user)).thenReturn(response);

        ResponseEntity<Page<UserResponse>> result = userController.getAll(0, 10);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(1, result.getBody().getTotalElements());
    }

    @Test
    void create_success() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        User user = new User();
        User created = new User();
        CreateUserRequest response = new CreateUserRequest();
        Mockito.when(userMapper.toEntity(createUserRequest)).thenReturn(user);
        Mockito.when(userService.create(user)).thenReturn(created);
        Mockito.when(userMapper.toCreateDto(created)).thenReturn(response);

        ResponseEntity<CreateUserRequest> result = userController.create(createUserRequest);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertEquals(response, result.getBody());
    }

    @Test
    void update_success() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        User user = new User();
        User updated = new User();
        UpdateUserRequest response = new UpdateUserRequest();
        Mockito.when(userMapper.toEntity(updateUserRequest)).thenReturn(user);
        Mockito.when(userService.update(1L, user)).thenReturn(updated);
        Mockito.when(userMapper.toUpdateDto(updated)).thenReturn(response);

        ResponseEntity<UpdateUserRequest> result = userController.update(1L, updateUserRequest);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(response, result.getBody());
    }
}