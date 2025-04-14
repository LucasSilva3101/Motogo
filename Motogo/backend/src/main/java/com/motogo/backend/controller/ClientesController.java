package com.motogo.backend.controller;

import com.motogo.backend.model.Clientes;
import com.motogo.backend.model.Motos;
import com.motogo.backend.service.ClientesService;
import com.motogo.backend.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    @Autowired
    private ClientesService clientesService;

    @GetMapping
    public List<Clientes> listarClientes() {
        return clientesService.listarTodas();
    }

    @PostMapping
    public Clientes criarClientes(@RequestBody Clientes clientes) {
        return clientesService.salvar(clientes);
    }

    @DeleteMapping("/{id}")
    public void deletarClientes(@PathVariable Long id) {
        clientesService.deletar(id);
    }
}
