package com.motogo.backend.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AluguelCreateRequest(

        @NotNull(message = "O cliente é obrigatório")
        Long clienteId,

        @NotNull(message = "A moto é obrigatória")
        Long motoId,

        @NotNull(message = "A data de início é obrigatória")
        LocalDate dataInicio,

        LocalDate dataFim

) { }