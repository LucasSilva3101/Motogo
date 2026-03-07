package com.motogo.backend.service;

import com.motogo.backend.dto.*;
import com.motogo.backend.exception.AuthException;
import com.motogo.backend.exception.ClienteException;
import com.motogo.backend.model.*;
import com.motogo.backend.repository.*;
import com.motogo.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final ClientesRepository clientesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDTO register(RegisterRequestDTO dto) {

        if (usuarioRepository.existsByEmailIgnoreCase(dto.email())) {
            throw new ClienteException("email ja cadastrado");
        }

        UserAdm userAdm = UserAdm.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .build();

        usuarioRepository.save(userAdm);

        Clientes cliente = new Clientes();
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setEndereco(dto.endereco());

        clientesRepository.save(cliente);

        String token = jwtService.generateToken(userAdm.getEmail());

        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {

        UserAdm userAdm = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new AuthException("usuario nao encontrado"));

        if (!passwordEncoder.matches(dto.password(), userAdm.getPassword())) {
            throw new AuthException("senha incorreta");
        }

        String token = jwtService.generateToken(userAdm.getEmail());

        return new AuthResponseDTO(token);
    }
}