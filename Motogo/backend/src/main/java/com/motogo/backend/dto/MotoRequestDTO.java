package com.motogo.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MotoRequestDTO(

        @NotBlank(message = "O modelo da moto é obrigatório")
        String modelo,

        @NotBlank(message = "A marca da moto é obrigatória")
        String marca,

        @NotNull(message = "O preço por dia é obrigatório")
        @Positive(message = "O preço por dia deve ser maior que zero")
        BigDecimal precoPorDia,

        @NotNull(message = "O campo 'disponivel' é obrigatório")
        Boolean disponivel

) { }