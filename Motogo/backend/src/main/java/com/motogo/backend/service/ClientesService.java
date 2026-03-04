package com.motogo.backend.service;

import com.motogo.backend.exception.ClienteException;
import com.motogo.backend.model.Clientes;
import com.motogo.backend.repository.ClientesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientesService {

    private final ClientesRepository clientesRepository;
    private final EmailService emailService;

    public Page<Clientes> listarTodas(Pageable pageable) {
        return clientesRepository.findAll(pageable);
    }

    public Optional<Clientes> findById(Long id) {
        return clientesRepository.findById(id);
    }

    public Clientes salvar(Clientes cliente) {

        if (clientesRepository.existsByEmailIgnoreCase(cliente.getEmail())) {
            throw new ClienteException("email ja cadastrado");
        }

        Clientes clienteSalvo = clientesRepository.save(cliente);

        try {
            emailService.enviarEmailBoasVindas(clienteSalvo.getEmail(), clienteSalvo.getNome());
        } catch (Exception e) {
            throw new ClienteException("Não consegui enviar o e-mail de boas-vindas.");
        }

        return clienteSalvo;
    }

    public Clientes atualizar(Long id, Clientes clienteAtualizado) {
        Optional<Clientes> clienteExistenteOpt = clientesRepository.findById(id);

        if (clienteExistenteOpt.isEmpty()) {
            throw new ClienteException("Cliente com ID " + id + " não foi encontrado.");
        }

        // 🔹 regra extra: não deixar trocar para um email que já existe em outro cliente
        if (clientesRepository.existsByEmailIgnoreCaseAndIdNot(
                clienteAtualizado.getEmail(), id
        )) {
            throw new ClienteException("email ja cadastrado");
        }

        Clientes cliente = clienteExistenteOpt.get();

        cliente.setNome(clienteAtualizado.getNome());
        cliente.setEmail(clienteAtualizado.getEmail());
        cliente.setTelefone(clienteAtualizado.getTelefone());
        cliente.setEndereco(clienteAtualizado.getEndereco());
        cliente.setDataNasc(clienteAtualizado.getDataNasc());

        try {
            return clientesRepository.save(cliente);
        } catch (Exception e) {
            throw new ClienteException("Não consegui atualizar o cliente com ID " + id + ".");
        }
    }

    public void deletar(Long id) {
        try {
            clientesRepository.deleteById(id);
        } catch (Exception e) {
            throw new ClienteException("Não consegui excluir o cliente com ID " + id + ".");
        }
    }
}