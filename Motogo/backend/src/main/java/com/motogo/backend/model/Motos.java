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
@ToString
public class Motos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String modelo;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoPorDia;

    @Column(nullable = false)
    private Boolean disponivel;
}