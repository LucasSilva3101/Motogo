package com.motogo.backend.service;

import com.motogo.backend.dto.AluguelRequestDTO;
import com.motogo.backend.model.Alugueis;
import com.motogo.backend.model.Clientes;
import com.motogo.backend.model.Motos;
import com.motogo.backend.model.StatusAluguel;
import com.motogo.backend.repository.AluguelRepository;
import com.motogo.backend.repository.ClientesRepository;
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
    private final ClientesRepository clientesRepository;
    private final MotoRepository motoRepository;

    public List<Alugueis> listarTodos() {
        return aluguelRepository.findAll();
    }

    public Alugueis criarAluguel(AluguelRequestDTO dto) {

        Clientes cliente = clientesRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        Motos moto = motoRepository.findById(dto.motoId())
                .orElseThrow(() -> new RuntimeException("Moto não encontrada."));

        if (Boolean.FALSE.equals(moto.getDisponivel())) {
            throw new RuntimeException("Essa moto não está disponível para aluguel.");
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
            throw new RuntimeException("Ocorreu um erro ao salvar o aluguel: " + e.getMessage(), e);
        }
    }

    public Alugueis finalizarAluguel(Long aluguelId, LocalDate dataFim) {
        Alugueis aluguel = aluguelRepository.findById(aluguelId)
                .orElseThrow(() -> new RuntimeException("Aluguel não encontrado."));

        if (aluguel.getStatus() == StatusAluguel.FINALIZADO) {
            throw new RuntimeException("Esse aluguel já foi finalizado.");
        }

        if (aluguel.getStatus() == StatusAluguel.CANCELADO) {
            throw new RuntimeException("Não é possível finalizar um aluguel cancelado.");
        }

        if (dataFim.isBefore(aluguel.getDataInicio())) {
            throw new RuntimeException("A data de término não pode ser antes da data de início.");
        }

        aluguel.setDataFim(dataFim);

        // calcula o total com base nos dias e valor da moto
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
            throw new RuntimeException("Ocorreu um erro ao finalizar o aluguel: " + e.getMessage(), e);
        }
    }

    private BigDecimal calcularTotal(BigDecimal precoPorDia, LocalDate inicio, LocalDate fim) {
        long dias = ChronoUnit.DAYS.between(inicio, fim);
        if (dias <= 0) {
            dias = 1;
        }
        return precoPorDia.multiply(BigDecimal.valueOf(dias));
    }

    public Alugueis cancelarAluguel(Long aluguelId) {
        Alugueis aluguel = aluguelRepository.findById(aluguelId)
                .orElseThrow(() -> new RuntimeException("Aluguel não encontrado."));

        if (aluguel.getStatus() == StatusAluguel.FINALIZADO) {
            throw new RuntimeException("Não dá pra cancelar um aluguel já finalizado.");
        }

        if (aluguel.getStatus() == StatusAluguel.CANCELADO) {
            throw new RuntimeException("Esse aluguel já foi cancelado.");
        }

        aluguel.setStatus(StatusAluguel.CANCELADO);

        // devolve a moto como disponível
        Motos moto = aluguel.getMoto();
        moto.setDisponivel(true);
        motoRepository.save(moto);

        try {
            return aluguelRepository.save(aluguel);
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao cancelar o aluguel: " + e.getMessage(), e);
        }
    }
}