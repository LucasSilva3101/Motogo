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

        Usuario usuario = Usuario.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.ROLE_CLIENTE)
                .build();

        usuarioRepository.save(usuario);

        Clientes cliente = new Clientes();
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setEndereco(dto.endereco());
        cliente.setUsuario(usuario);

        clientesRepository.save(cliente);

        String token = jwtService.generateToken(usuario.getEmail());

        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new AuthException("usuario nao encontrado"));

        if (!passwordEncoder.matches(dto.password(), usuario.getPassword())) {
            throw new AuthException("senha incorreta");
        }

        String token = jwtService.generateToken(usuario.getEmail());

        return new AuthResponseDTO(token);
    }
}