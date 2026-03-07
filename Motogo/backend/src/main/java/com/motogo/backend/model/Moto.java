package com.motogo.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "motos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String marca;

    @Column(name = "preco_por_dia", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoPorDia;

    @Column(nullable = false)
    private Boolean disponivel;
}