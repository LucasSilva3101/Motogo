package com.motogo.backend.service;

import com.motogo.backend.model.Clientes;
import com.motogo.backend.model.Motos;
import com.motogo.backend.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientesService {

    @Autowired
    private ClientesRepository clientesRepository;

    public List<Clientes> listarTodas() {
        return clientesRepository.findAll();
    }

    public Clientes salvar(Clientes clientes) {
        try {
            return clientesRepository.save(clientes);
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro inesperado ao salvar um cliente: " + e.getMessage(), e);
        }
    }

    public void deletar(Long id) {
        clientesRepository.deleteById(id);
    }
}
