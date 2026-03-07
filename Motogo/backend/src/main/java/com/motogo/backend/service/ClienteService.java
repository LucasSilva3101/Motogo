package com.motogo.backend.service;

import com.motogo.backend.dto.request.cliente.ClienteUpdateRequest;
import com.motogo.backend.exception.ClienteException;
import com.motogo.backend.model.Cliente;
import com.motogo.backend.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Page<Cliente> listarTodos(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException("Cliente não encontrado."));
    }

    public Cliente buscarPorEmail(String email) {
        return clienteRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ClienteException("Cliente não encontrado."));
    }

    public Cliente atualizar(Long id, ClienteUpdateRequest request) {
        Cliente cliente = buscarPorId(id);

        if (clienteRepository.existsByEmailIgnoreCaseAndIdNot(request.email(), id)) {
            throw new ClienteException("Já existe outro cliente com esse e-mail.");
        }

        cliente.setNome(request.nome());
        cliente.setEmail(request.email());
        cliente.setTelefone(request.telefone());
        cliente.setEndereco(request.endereco());
        cliente.setDataNasc(request.dataNasc());

        return clienteRepository.save(cliente);
    }

    public void deletar(Long id) {
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }
}