package com.motogo.backend.controller;

import com.motogo.backend.model.Motos;
import com.motogo.backend.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motos")
@CrossOrigin(origins = "http://localhost:4200")
public class MotoController {

    @Autowired
    private MotoService motoService;

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
