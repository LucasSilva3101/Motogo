package com.motogo.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MotoUpdateRequest(
        @NotBlank String modelo,
        @NotBlank String marca,
        @NotNull @Positive BigDecimal precoPorDia,
        @NotNull Boolean disponivel
) {}