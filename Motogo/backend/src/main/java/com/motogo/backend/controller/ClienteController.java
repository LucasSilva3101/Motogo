package com.motogo.backend.controller;

import com.motogo.backend.dto.request.cliente.ClienteUpdateRequest;
import com.motogo.backend.dto.response.ClienteResponse;
import com.motogo.backend.model.Cliente;
import com.motogo.backend.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public Page<ClienteResponse> listarTodos(Pageable pageable) {
        return clienteService.listarTodos(pageable).map(this::toResponse);
    }

    @GetMapping("/{id}")
    public ClienteResponse buscarPorId(@PathVariable Long id) {
        return toResponse(clienteService.buscarPorId(id));
    }

    @GetMapping("/me")
    public ClienteResponse meuPerfil(Authentication authentication) {
        return toResponse(clienteService.buscarPorEmail(authentication.getName()));
    }

    @PutMapping("/{id}")
    public ClienteResponse atualizar(@PathVariable Long id, @RequestBody ClienteUpdateRequest request) {
        return toResponse(clienteService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        clienteService.deletar(id);
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getEndereco(),
                cliente.getDataNasc()
        );
    }
}