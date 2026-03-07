package com.motogo.backend.controller;

import com.motogo.backend.dto.request.auth.AdminLoginRequest;
import com.motogo.backend.dto.request.auth.AdminRegisterRequest;
import com.motogo.backend.dto.response.auth.AuthResponse;
import com.motogo.backend.service.UserAdmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserAdmController {

    private final UserAdmService userAdmService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AdminRegisterRequest dto) {
        return ResponseEntity.ok(userAdmService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AdminLoginRequest dto) {
        return ResponseEntity.ok(userAdmService.login(dto));
    }
}