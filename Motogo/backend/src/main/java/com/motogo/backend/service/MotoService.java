package com.motogo.backend.service;

import com.motogo.backend.model.Motos;
import com.motogo.backend.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    public List<Motos> listarTodas() {
        return motoRepository.findAll();
    }

    public Motos salvar(Motos moto) {
        try {
            return motoRepository.save(moto);
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro inesperado ao salvar a moto: " + e.getMessage(), e);
        }
    }

    public void deletar(Long id) {
        motoRepository.deleteById(id);
    }
}
