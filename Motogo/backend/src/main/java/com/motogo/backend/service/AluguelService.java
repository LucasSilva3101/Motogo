package com.motogo.backend.service;

import com.motogo.backend.dto.AluguelRequestDTO;
import com.motogo.backend.exception.AluguelException;
import com.motogo.backend.model.Alugueis;
import com.motogo.backend.model.Clientes;
import com.motogo.backend.model.Motos;
import com.motogo.backend.model.StatusAluguel;
import com.motogo.backend.repository.AluguelRepository;
import com.motogo.backend.repository.ClientesRepository;
import com.motogo.backend.repository.MotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AluguelService {

    private final AluguelRepository aluguelRepository;
    private final ClientesRepository clientesRepository;
    private final MotoRepository motoRepository;

    public Page<Alugueis> listarTodos(Pageable pageable) {
        return aluguelRepository.findAll(pageable);
    }

    public Alugueis criarAluguel(AluguelRequestDTO dto) {

        Clientes cliente = clientesRepository.findById(dto.clienteId())
                .orElseThrow(() -> new AluguelException("Cliente do aluguel não foi encontrado."));

        Motos moto = motoRepository.findById(dto.motoId())
                .orElseThrow(() -> new AluguelException("Moto do aluguel não foi encontrada."));

        if (Boolean.FALSE.equals(moto.getDisponivel())) {
            throw new AluguelException("Essa moto não está disponível pra aluguel.");
        }

        Alugueis aluguel = new Alugueis();
        aluguel.setCliente(cliente);
        aluguel.setMoto(moto);
        aluguel.setDataInicio(dto.dataInicio());
        aluguel.setDataFim(dto.dataFim());
        aluguel.setStatus(StatusAluguel.EM_ANDAMENTO);

        if (dto.dataFim() != null) {
            aluguel.setTotalPago(calcularTotal(moto.getPrecoPorDia(), dto.dataInicio(), dto.dataFim()));
        }

        moto.setDisponivel(false);
        motoRepository.save(moto);

        try {
            return aluguelRepository.save(aluguel);
        } catch (Exception e) {
            throw new AluguelException("Não consegui salvar esse aluguel.");
        }
    }

    public Alugueis finalizarAluguel(Long aluguelId, LocalDate dataFim) {
        Alugueis aluguel = aluguelRepository.findById(aluguelId)
                .orElseThrow(() -> new AluguelException("Aluguel não foi encontrado."));

        if (aluguel.getStatus() == StatusAluguel.FINALIZADO) {
            throw new AluguelException("Esse aluguel já está finalizado.");
        }

        if (aluguel.getStatus() == StatusAluguel.CANCELADO) {
            throw new AluguelException("Não dá pra finalizar um aluguel cancelado.");
        }

        if (dataFim.isBefore(aluguel.getDataInicio())) {
            throw new AluguelException("A data final não pode ser antes da data inicial.");
        }

        aluguel.setDataFim(dataFim);

        BigDecimal total = calcularTotal(
                aluguel.getMoto().getPrecoPorDia(),
                aluguel.getDataInicio(),
                dataFim
        );

        aluguel.setTotalPago(total);
        aluguel.setStatus(StatusAluguel.FINALIZADO);

        Motos moto = aluguel.getMoto();
        moto.setDisponivel(true);
        motoRepository.save(moto);

        try {
            return aluguelRepository.save(aluguel);
        } catch (Exception e) {
            throw new AluguelException("Não consegui finalizar esse aluguel.");
        }
    }

    public Alugueis cancelarAluguel(Long aluguelId) {
        Alugueis aluguel = aluguelRepository.findById(aluguelId)
                .orElseThrow(() -> new AluguelException("Aluguel não foi encontrado."));

        if (aluguel.getStatus() == StatusAluguel.FINALIZADO) {
            throw new AluguelException("Não dá pra cancelar um aluguel já finalizado.");
        }

        if (aluguel.getStatus() == StatusAluguel.CANCELADO) {
            throw new AluguelException("Esse aluguel já está cancelado.");
        }

        aluguel.setStatus(StatusAluguel.CANCELADO);

        Motos moto = aluguel.getMoto();
        moto.setDisponivel(true);
        motoRepository.save(moto);

        try {
            return aluguelRepository.save(aluguel);
        } catch (Exception e) {
            throw new AluguelException("Não consegui cancelar esse aluguel.");
        }
    }

    private BigDecimal calcularTotal(BigDecimal precoPorDia, LocalDate inicio, LocalDate fim) {
        long dias = ChronoUnit.DAYS.between(inicio, fim);
        if (dias <= 0) {
            dias = 1;
        }
        return precoPorDia.multiply(BigDecimal.valueOf(dias));
    }
}