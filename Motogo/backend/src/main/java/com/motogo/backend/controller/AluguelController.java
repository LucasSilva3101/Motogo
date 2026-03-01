package com.motogo.backend.controller;

import com.motogo.backend.dto.AluguelRequestDTO;
import com.motogo.backend.dto.AluguelResponseDTO;
import com.motogo.backend.dto.FinalizarAluguelRequestDTO;
import com.motogo.backend.model.Alugueis;
import com.motogo.backend.service.AluguelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alugueis")
@RequiredArgsConstructor
public class AluguelController {

    private final AluguelService aluguelService;

    @GetMapping
    public Page<AluguelResponseDTO> listarAlugueis(Pageable pageable) {
        return aluguelService.listarTodos(pageable)
                .map(this::toResponseDTO);
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