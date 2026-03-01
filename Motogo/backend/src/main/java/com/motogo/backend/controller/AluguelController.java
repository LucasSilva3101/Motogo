package com.motogo.backend.controller;

import com.motogo.backend.dto.AluguelRequestDTO;
import com.motogo.backend.dto.AluguelResponseDTO;
import com.motogo.backend.dto.FinalizarAluguelRequestDTO;
import com.motogo.backend.model.Alugueis;
import com.motogo.backend.service.AluguelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alugueis")
@RequiredArgsConstructor
public class AluguelController {

    private final AluguelService aluguelService;

    @GetMapping
    public List<AluguelResponseDTO> listarAlugueis() {
        return aluguelService.listarTodos()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public AluguelResponseDTO criarAluguel(@Valid @RequestBody AluguelRequestDTO dto) {
        Alugueis aluguel = aluguelService.criarAluguel(dto);
        return toResponseDTO(aluguel);
    }

    @PutMapping("/{id}/finalizar")
    public AluguelResponseDTO finalizarAluguel(
            @PathVariable Long id,
            @Valid @RequestBody FinalizarAluguelRequestDTO dto
    ) {
        Alugueis aluguel = aluguelService.finalizarAluguel(id, dto.dataFim());
        return toResponseDTO(aluguel);
    }

    @PutMapping("/{id}/cancelar")
    public AluguelResponseDTO cancelarAluguel(@PathVariable Long id) {
        Alugueis aluguel = aluguelService.cancelarAluguel(id);
        return toResponseDTO(aluguel);
    }

    private AluguelResponseDTO toResponseDTO(Alugueis aluguel) {
        return new AluguelResponseDTO(
                aluguel.getId(),
                aluguel.getCliente().getId(),
                aluguel.getCliente().getNome(),
                aluguel.getMoto().getId(),
                aluguel.getMoto().getModelo(),
                aluguel.getDataInicio(),
                aluguel.getDataFim(),
                aluguel.getStatus() != null ? aluguel.getStatus().name() : null,
                aluguel.getTotalPago()
        );
    }
}