package com.motogo.backend.controller;

import com.motogo.backend.dto.request.AluguelCreateRequest;
import com.motogo.backend.dto.request.AluguelFinalizarRequest;
import com.motogo.backend.dto.response.AluguelResponse;
import com.motogo.backend.model.Aluguel;
import com.motogo.backend.service.AluguelService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alugueis")
@RequiredArgsConstructor
public class AluguelController {

    private final AluguelService aluguelService;

    @GetMapping
    public List<AluguelResponse> listar() {
        return aluguelService.listarTodos().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public AluguelResponse buscarPorId(@PathVariable Long id) {
        return toResponse(aluguelService.buscarPorId(id));
    }

    @PostMapping
    public AluguelResponse criar(@RequestBody AluguelCreateRequest request) {
        return toResponse(aluguelService.criar(request));
    }

    @PutMapping("/{id}/finalizar")
    public AluguelResponse finalizar(@PathVariable Long id, @RequestBody AluguelFinalizarRequest request) {
        return toResponse(aluguelService.finalizar(id, request.dataFim()));
    }

    @PutMapping("/{id}/cancelar")
    public AluguelResponse cancelar(@PathVariable Long id) {
        return toResponse(aluguelService.cancelar(id));
    }

    private AluguelResponse toResponse(Aluguel aluguel) {
        return new AluguelResponse(
                aluguel.getId(),
                aluguel.getCliente().getId(),
                aluguel.getCliente().getNome(),
                aluguel.getMoto().getId(),
                aluguel.getMoto().getModelo(),
                aluguel.getDataInicio(),
                aluguel.getDataFim(),
                aluguel.getStatus().name(),
                aluguel.getTotalPago()
        );
    }
}