package com.motogo.backend.dto.response.auth;

public record AuthResponse(
        String token,
        String tipo,
        String email
) {}