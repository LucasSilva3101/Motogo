package com.motogo.backend.dto.request.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ClienteUpdateRequest(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String telefone,
        @NotBlank String endereco,
        LocalDate dataNasc
) {}