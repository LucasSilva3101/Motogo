package com.motogo.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "motos")
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

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public BigDecimal getPrecoPorDia() { return precoPorDia; }
    public void setPrecoPorDia(BigDecimal precoPorDia) { this.precoPorDia = precoPorDia; }

    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }
}