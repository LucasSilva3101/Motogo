package com.motogo.backend.controller;

import com.motogo.backend.dto.request.moto.MotoCreateRequest;
import com.motogo.backend.dto.request.MotoUpdateRequest;
import com.motogo.backend.dto.response.MotoResponse;
import com.motogo.backend.model.Moto;
import com.motogo.backend.service.MotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/motos")
@RequiredArgsConstructor
public class MotoController {

    private final MotoService motoService;

    @GetMapping
    public Page<MotoResponse> listar(Pageable pageable) {
        return motoService.listarTodas(pageable).map(this::toResponse);
    }

    @GetMapping("/{id}")
    public MotoResponse buscarPorId(@PathVariable Long id) {
        return toResponse(motoService.buscarPorId(id));
    }

    @PostMapping
    public MotoResponse criar(@RequestBody MotoCreateRequest request) {
        return toResponse(motoService.criar(request));
    }

    @PutMapping("/{id}")
    public MotoResponse atualizar(@PathVariable Long id, @RequestBody MotoUpdateRequest request) {
        return toResponse(motoService.atualizar(id, request));
    }

    @PutMapping("/{id}/desabilitar")
    public MotoResponse desabilitar(@PathVariable Long id) {
        return toResponse(motoService.desabilitar(id));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        motoService.deletar(id);
    }

    private MotoResponse toResponse(Moto moto) {
        return new MotoResponse(
                moto.getId(),
                moto.getModelo(),
                moto.getMarca(),
                moto.getPrecoPorDia(),
                moto.getDisponivel()
        );
    }
}