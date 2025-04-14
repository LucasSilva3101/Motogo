package com.motogo.backend.controller;

import com.motogo.backend.model.Clientes;
import com.motogo.backend.model.Motos;
import com.motogo.backend.service.ClientesService;
import com.motogo.backend.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    @Autowired
    private ClientesService clientesService;

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
