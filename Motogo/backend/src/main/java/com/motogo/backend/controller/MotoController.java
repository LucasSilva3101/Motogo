package com.motogo.backend.controller;

import com.motogo.backend.dto.MotoRequestDTO;
import com.motogo.backend.dto.MotoResponseDTO;
import com.motogo.backend.model.Motos;
import com.motogo.backend.service.MotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/motos")
@RequiredArgsConstructor
public class MotoController {

    private final MotoService motoService;

    @GetMapping
    public List<MotoResponseDTO> listarMotos() {
        return motoService.listarTodas()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public MotoResponseDTO criarMoto(@Valid @RequestBody MotoRequestDTO dto) {
        Motos moto = toEntity(dto);
        Motos salva = motoService.salvar(moto);
        return toResponseDTO(salva);
    }

    @DeleteMapping("/{id}")
    public void deletarMoto(@PathVariable Long id) {
        motoService.deletar(id);
    }

    private Motos toEntity(MotoRequestDTO dto) {
        Motos moto = new Motos();
        moto.setModelo(dto.modelo());
        moto.setMarca(dto.marca());
        moto.setPrecoPorDia(dto.precoPorDia());
        moto.setDisponivel(dto.disponivel());
        return moto;
    }

    private MotoResponseDTO toResponseDTO(Motos moto) {
        return new MotoResponseDTO(
                moto.getId(),
                moto.getModelo(),
                moto.getMarca(),
                moto.getPrecoPorDia(),
                moto.getDisponivel()
        );
    }
}