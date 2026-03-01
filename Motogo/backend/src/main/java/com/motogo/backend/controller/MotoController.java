package com.motogo.backend.controller;

import com.motogo.backend.model.Motos;
import com.motogo.backend.service.MotoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/motos")
@RequiredArgsConstructor
public class MotoController {

    private final MotoService motoService;

    @GetMapping
    public List<Motos> listarMotos() {
        return motoService.listarTodas();
    }

    @PostMapping
    public Motos criarMoto(@RequestBody Motos moto) {
        return motoService.salvar(moto);
    }

    @DeleteMapping("/{id}")
    public void deletarMoto(@PathVariable Long id) {
        motoService.deletar(id);
    }
}