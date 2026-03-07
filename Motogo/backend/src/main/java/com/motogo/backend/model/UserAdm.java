package com.motogo.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_adm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAdm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}