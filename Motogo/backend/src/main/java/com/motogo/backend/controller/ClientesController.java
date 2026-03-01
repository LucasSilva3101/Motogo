package com.motogo.backend.controller;

import com.motogo.backend.dto.ClienteRequestDTO;
import com.motogo.backend.dto.ClienteResponseDTO;
import com.motogo.backend.model.Clientes;
import com.motogo.backend.service.ClientesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClientesController {

    private final ClientesService clientesService;

    @GetMapping
    public List<ClienteResponseDTO> listarClientes() {
        return clientesService.listarTodas()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id) {
        Optional<Clientes> cliente = clientesService.findById(id);
        return cliente
                .map(this::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criarClientes(@Valid @RequestBody ClienteRequestDTO dto) {
        Clientes entidade = toEntity(dto);
        Clientes salvo = clientesService.salvar(entidade);
        return ResponseEntity.ok(toResponseDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO dto
    ) {
        Clientes entidadeAtualizada = toEntity(dto);
        Clientes salvo = clientesService.atualizar(id, entidadeAtualizada);
        return ResponseEntity.ok(toResponseDTO(salvo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarClientes(@PathVariable Long id) {
        clientesService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // --- MÉTODOS DE MAPEAMENTO ---

    private ClienteResponseDTO toResponseDTO(Clientes cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getEndereco(),
                cliente.getDataNasc()
        );
    }

    private Clientes toEntity(ClienteRequestDTO dto) {
        Clientes cliente = new Clientes();
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setEndereco(dto.endereco());
        cliente.setDataNasc(dto.dataNasc());
        return cliente;
    }
}