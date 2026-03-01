package com.motogo.backend.controller;

import com.motogo.backend.model.Clientes;
import com.motogo.backend.service.ClientesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClientesController {

    private final ClientesService clientesService;

    @GetMapping
    public List<Clientes> listarClientes() {
        return clientesService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clientes> findById(@PathVariable Long id) {
        Optional<Clientes> cliente = clientesService.findById(id);
        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Clientes criarClientes(@RequestBody Clientes clientes) {
        return clientesService.salvar(clientes);
    }

    @PutMapping("/{id}")
    public Clientes atualizarCliente(@PathVariable Long id, @RequestBody Clientes clienteAtualizado) {
        return clientesService.atualizar(id, clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletarClientes(@PathVariable Long id) {
        clientesService.deletar(id);
    }
}