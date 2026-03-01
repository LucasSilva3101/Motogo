package com.motogo.backend.dto;

import java.math.BigDecimal;

public record MotoResponseDTO(
        Long id,
        String modelo,
        String marca,
        BigDecimal precoPorDia,
        Boolean disponivel
) { }