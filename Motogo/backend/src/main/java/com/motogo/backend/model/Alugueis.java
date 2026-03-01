package com.motogo.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "alugueis")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Alugueis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Clientes cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moto_id", nullable = false)
    private Motos moto;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column
    private LocalDate dataFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusAluguel status;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalPago;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Motos getMoto() {
        return moto;
    }

    public void setMoto(Motos moto) {
        this.moto = moto;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public StatusAluguel getStatus() {
        return status;
    }

    public void setStatus(StatusAluguel status) {
        this.status = status;
    }

    public BigDecimal getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(BigDecimal totalPago) {
        this.totalPago = totalPago;
    }
}