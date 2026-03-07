package com.motogo.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "alugueis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Alugueis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Clientes cliente;

    @ManyToOne
    @JoinColumn(name = "moto_id", nullable = false)
    private Motos moto;

    private LocalDate dataInicio;
    private LocalDate dataFim;

    @Enumerated(EnumType.STRING)
    private StatusAluguel status;

    @Column(name = "total_pago", precision = 10, scale = 2)
    private BigDecimal totalPago;
}