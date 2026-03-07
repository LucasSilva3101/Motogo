package com.motogo.backend.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AluguelFinalizarRequest(
        @NotNull LocalDate dataFim
) {}