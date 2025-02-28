package com.motogo.backend.service;

import com.motogo.backend.model.Moto;
import com.motogo.backend.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    public List<Moto> listarTodas() {
        return motoRepository.findAll();
    }

    public Moto salvar(Moto moto) {
        return motoRepository.save(moto);
    }

    public void deletar(Long id) {
        motoRepository.deleteById(id);
    }
}
