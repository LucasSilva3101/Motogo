package com.motogo.backend.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record FinalizarAluguelRequestDTO(

        @NotNull(message = "A data de término é obrigatória")
        LocalDate dataFim

) { }