package com.qintess.benefits.card.system.controller;

import com.qintess.benefits.card.system.domain.User;
import com.qintess.benefits.card.system.domain.dto.CreateUserRequestDTO;
import com.qintess.benefits.card.system.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequestDTO request) {
        User createdUser = userService.create(request);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.list();
        return ResponseEntity.ok(users);
    }
}
