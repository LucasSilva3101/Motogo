package com.motogo.backend.controller;

import com.motogo.backend.dto.request.auth.AdminLoginRequest;
import com.motogo.backend.dto.request.auth.AdminRegisterRequest;
import com.motogo.backend.dto.request.auth.ClienteLoginRequest;
import com.motogo.backend.dto.request.auth.ClienteRegisterRequest;
import com.motogo.backend.dto.response.auth.AuthResponse;
import com.motogo.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/clientes/register")
    public AuthResponse registerCliente(@Valid @RequestBody ClienteRegisterRequest request) {
        return authService.registerCliente(request);
    }

    @PostMapping("/clientes/login")
    public AuthResponse loginCliente(@Valid @RequestBody ClienteLoginRequest request) {
        return authService.loginCliente(request);
    }

    @PostMapping("/admin/register")
    public AuthResponse registerAdmin(@Valid @RequestBody AdminRegisterRequest request) {
        return authService.registerAdmin(request);
    }

    @PostMapping("/admin/login")
    public AuthResponse loginAdmin(@Valid @RequestBody AdminLoginRequest request) {
        return authService.loginAdmin(request);
    }
}