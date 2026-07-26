package com.nvs.Mavli.controller;

import com.nvs.Mavli.dto.UserRequestDTO;
import com.nvs.Mavli.dto.UserResponseDTO;
import com.nvs.Mavli.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(userService.createUser(request));
    }
}