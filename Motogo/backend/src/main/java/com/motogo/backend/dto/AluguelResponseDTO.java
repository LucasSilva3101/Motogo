package com.motogo.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AluguelResponseDTO(
        Long id,
        Long clienteId,
        String clienteNome,
        Long motoId,
        String motoModelo,
        LocalDate dataInicio,
        LocalDate dataFim,
        String status,
        BigDecimal totalPago
) { }