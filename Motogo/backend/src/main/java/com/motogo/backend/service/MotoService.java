package com.motogo.backend.service;

import com.motogo.backend.exception.MotoException;
import com.motogo.backend.model.Motos;
import com.motogo.backend.repository.MotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MotoService {

    private final MotoRepository motoRepository;

    public Page<Motos> listarTodas(Pageable pageable) {
        return motoRepository.findAll(pageable);
    }

    public Motos salvar(Motos moto) {
        try {
            return motoRepository.save(moto);
        } catch (Exception e) {
            throw new MotoException("Não consegui salvar essa moto.");
        }
    }

    public void deletar(Long id) {
        try {
            motoRepository.deleteById(id);
        } catch (Exception e) {
            throw new MotoException("Não consegui excluir a moto com ID " + id + ".");
        }
    }
}