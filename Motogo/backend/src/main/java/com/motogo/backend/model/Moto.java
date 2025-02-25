package com.motogo.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "motos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelo;
    private String marca;
    private double precoPorDia;
    private boolean disponivel;
}
