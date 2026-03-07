package com.motogo.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ClienteRequestDTO(

        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        String senha,

        @NotBlank(message = "O telefone é obrigatório")
        String telefone,

        @NotBlank(message = "O endereço é obrigatório")
        String endereco,

        LocalDate dataNasc

) {}