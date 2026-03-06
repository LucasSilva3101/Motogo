package com.motogo.backend.dto;

public record LoginRequestDTO(
        String email,
        String password
) {}