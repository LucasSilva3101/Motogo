package com.motogo.backend.service;

import com.motogo.backend.dto.request.moto.MotoCreateRequest;
import com.motogo.backend.dto.request.MotoUpdateRequest;
import com.motogo.backend.exception.MotoException;
import com.motogo.backend.model.Moto;
import com.motogo.backend.repository.MotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MotoService {

    private final MotoRepository motoRepository;

    public Page<Moto> listarTodas(Pageable pageable) {
        return motoRepository.findAll(pageable);
    }

    public Moto buscarPorId(Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new MotoException("Moto não encontrada."));
    }

    public Moto criar(MotoCreateRequest request) {
        Moto moto = Moto.builder()
                .modelo(request.modelo())
                .marca(request.marca())
                .precoPorDia(request.precoPorDia())
                .disponivel(request.disponivel())
                .build();

        return motoRepository.save(moto);
    }

    public Moto atualizar(Long id, MotoUpdateRequest request) {
        Moto moto = buscarPorId(id);
        moto.setModelo(request.modelo());
        moto.setMarca(request.marca());
        moto.setPrecoPorDia(request.precoPorDia());
        moto.setDisponivel(request.disponivel());
        return motoRepository.save(moto);
    }

    public Moto desabilitar(Long id) {
        Moto moto = buscarPorId(id);
        moto.setDisponivel(false);
        return motoRepository.save(moto);
    }

    public void deletar(Long id) {
        Moto moto = buscarPorId(id);
        motoRepository.delete(moto);
    }
}