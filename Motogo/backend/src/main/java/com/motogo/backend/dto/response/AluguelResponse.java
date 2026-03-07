package com.motogo.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AluguelResponse(
        Long id,
        Long clienteId,
        String clienteNome,
        Long motoId,
        String motoModelo,
        LocalDate dataInicio,
        LocalDate dataFim,
        String status,
        BigDecimal totalPago
) {}