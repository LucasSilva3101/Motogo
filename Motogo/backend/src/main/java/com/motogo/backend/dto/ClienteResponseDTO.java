package com.motogo.backend.dto;

import java.time.LocalDate;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String endereco,
        LocalDate dataNasc
) { }