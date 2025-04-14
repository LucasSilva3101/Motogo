package com.motogo.backend.service;

import com.motogo.backend.model.Clientes;
import com.motogo.backend.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientesService {

    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private EmailService emailService;

    public List<Clientes> listarTodas() {
        return clientesRepository.findAll();
    }

    public Optional<Clientes> findById(Long id) {
        return clientesRepository.findById(id);
    }

    public Clientes salvar(Clientes cliente) {
        Clientes clienteSalvo = clientesRepository.save(cliente);

//        try {
//            emailService.enviarEmailBoasVindas(clienteSalvo.getEmail(), clienteSalvo.getNome());
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Ocorreu um erro inesperado ao salvar um cliente: " + e.getMessage(), e);
//        }

        return clienteSalvo;
    }

    public Clientes atualizar(Long id, Clientes clienteAtualizado) {
        Optional<Clientes> clienteExistente = clientesRepository.findById(id);

        if (clienteExistente.isPresent()) {
            Clientes cliente = clienteExistente.get();

            cliente.setNome(clienteAtualizado.getNome());
            cliente.setEmail(clienteAtualizado.getEmail());
            cliente.setTelefone(clienteAtualizado.getTelefone());
            cliente.setEndereco(clienteAtualizado.getEndereco());
            cliente.setDataNasc(clienteAtualizado.getDataNasc());

            return clientesRepository.save(cliente);
        } else {
            throw new RuntimeException("Cliente com ID " + id + " não encontrado.");
        }
    }

    public void deletar(Long id) {
        clientesRepository.deleteById(id);
    }
}
