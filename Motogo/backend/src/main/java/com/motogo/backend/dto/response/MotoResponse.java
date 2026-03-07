package com.motogo.backend.dto.response;

import java.math.BigDecimal;

public record MotoResponse(
        Long id,
        String modelo,
        String marca,
        BigDecimal precoPorDia,
        Boolean disponivel
) {}