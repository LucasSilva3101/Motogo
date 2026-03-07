package com.motogo.backend.service;

import com.motogo.backend.dto.request.AluguelCreateRequest;
import com.motogo.backend.exception.AluguelException;
import com.motogo.backend.model.*;
import com.motogo.backend.repository.AluguelRepository;
import com.motogo.backend.repository.ClienteRepository;
import com.motogo.backend.repository.MotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AluguelService {

    private final AluguelRepository aluguelRepository;
    private final ClienteRepository clienteRepository;
    private final MotoRepository motoRepository;

    public List<Aluguel> listarTodos() {
        return aluguelRepository.findAll();
    }

    public List<Aluguel> listarPorCliente(Long clienteId) {
        return aluguelRepository.findByClienteId(clienteId);
    }

    public Aluguel buscarPorId(Long id) {
        return aluguelRepository.findById(id)
                .orElseThrow(() -> new AluguelException("Aluguel não encontrado."));
    }

    public Aluguel criar(AluguelCreateRequest request) {
        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new AluguelException("Cliente não encontrado."));

        Moto moto = motoRepository.findById(request.motoId())
                .orElseThrow(() -> new AluguelException("Moto não encontrada."));

        if (Boolean.FALSE.equals(moto.getDisponivel())) {
            throw new AluguelException("Moto indisponível para aluguel.");
        }

        Aluguel aluguel = Aluguel.builder()
                .cliente(cliente)
                .moto(moto)
                .dataInicio(request.dataInicio())
                .dataFim(request.dataFim())
                .status(StatusAluguel.EM_ANDAMENTO)
                .totalPago(request.dataFim() != null
                        ? calcularTotal(moto.getPrecoPorDia(), request.dataInicio(), request.dataFim())
                        : null)
                .build();

        moto.setDisponivel(false);
        motoRepository.save(moto);

        return aluguelRepository.save(aluguel);
    }

    public Aluguel finalizar(Long aluguelId, LocalDate dataFim) {
        Aluguel aluguel = buscarPorId(aluguelId);

        if (aluguel.getStatus() == StatusAluguel.CANCELADO) {
            throw new AluguelException("Não é possível finalizar um aluguel cancelado.");
        }

        if (aluguel.getStatus() == StatusAluguel.FINALIZADO) {
            throw new AluguelException("Esse aluguel já está finalizado.");
        }

        if (dataFim.isBefore(aluguel.getDataInicio())) {
            throw new AluguelException("A data final não pode ser anterior à data inicial.");
        }

        aluguel.setDataFim(dataFim);
        aluguel.setTotalPago(calcularTotal(aluguel.getMoto().getPrecoPorDia(), aluguel.getDataInicio(), dataFim));
        aluguel.setStatus(StatusAluguel.FINALIZADO);

        Moto moto = aluguel.getMoto();
        moto.setDisponivel(true);
        motoRepository.save(moto);

        return aluguelRepository.save(aluguel);
    }

    public Aluguel cancelar(Long aluguelId) {
        Aluguel aluguel = buscarPorId(aluguelId);

        if (aluguel.getStatus() == StatusAluguel.FINALIZADO) {
            throw new AluguelException("Não é possível cancelar um aluguel finalizado.");
        }

        if (aluguel.getStatus() == StatusAluguel.CANCELADO) {
            throw new AluguelException("Esse aluguel já está cancelado.");
        }

        aluguel.setStatus(StatusAluguel.CANCELADO);

        Moto moto = aluguel.getMoto();
        moto.setDisponivel(true);
        motoRepository.save(moto);

        return aluguelRepository.save(aluguel);
    }

    private BigDecimal calcularTotal(BigDecimal precoPorDia, LocalDate inicio, LocalDate fim) {
        long dias = ChronoUnit.DAYS.between(inicio, fim);
        if (dias <= 0) {
            dias = 1;
        }
        return precoPorDia.multiply(BigDecimal.valueOf(dias));
    }
}