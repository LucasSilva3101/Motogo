package com.motogo.backend.dto.response;

import java.time.LocalDate;

public record ClienteResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        String endereco,
        LocalDate dataNasc
) {}