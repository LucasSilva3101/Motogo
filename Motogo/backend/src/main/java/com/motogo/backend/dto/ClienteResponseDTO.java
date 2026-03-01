package com.motogo.backend.dto;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String endereco,
        String dataNasc
) { }