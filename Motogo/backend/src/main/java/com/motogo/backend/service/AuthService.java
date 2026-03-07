package com.motogo.backend.service;

import com.motogo.backend.dto.request.auth.AdminLoginRequest;
import com.motogo.backend.dto.request.auth.AdminRegisterRequest;
import com.motogo.backend.dto.request.auth.ClienteLoginRequest;
import com.motogo.backend.dto.request.auth.ClienteRegisterRequest;
import com.motogo.backend.dto.response.auth.AuthResponse;
import com.motogo.backend.exception.AuthException;
import com.motogo.backend.model.Cliente;
import com.motogo.backend.model.Role;
import com.motogo.backend.model.UserAdm;
import com.motogo.backend.repository.ClienteRepository;
import com.motogo.backend.repository.UserAdmRepository;
import com.motogo.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final UserAdmRepository userAdmRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse registerCliente(ClienteRegisterRequest request) {
        if (clienteRepository.existsByEmailIgnoreCase(request.email())) {
            throw new AuthException("E-mail de cliente já cadastrado.");
        }

        Cliente cliente = Cliente.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .telefone(request.telefone())
                .endereco(request.endereco())
                .dataNasc(request.dataNasc())
                .build();

        clienteRepository.save(cliente);

        String token = jwtService.generateToken(cliente.getEmail(), Role.CLIENTE.name());

        return new AuthResponse(token, Role.CLIENTE.name(), cliente.getEmail());
    }

    public AuthResponse loginCliente(ClienteLoginRequest request) {
        Cliente cliente = clienteRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new AuthException("Cliente não encontrado."));

        if (!passwordEncoder.matches(request.senha(), cliente.getSenha())) {
            throw new AuthException("Senha inválida.");
        }

        String token = jwtService.generateToken(cliente.getEmail(), Role.CLIENTE.name());

        return new AuthResponse(token, Role.CLIENTE.name(), cliente.getEmail());
    }

    public AuthResponse registerAdmin(AdminRegisterRequest request) {
        if (userAdmRepository.existsByEmailIgnoreCase(request.email())) {
            throw new AuthException("E-mail de admin já cadastrado.");
        }

        UserAdm admin = UserAdm.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .role(Role.ADMIN)
                .build();

        userAdmRepository.save(admin);

        String token = jwtService.generateToken(admin.getEmail(), admin.getRole().name());

        return new AuthResponse(token, admin.getRole().name(), admin.getEmail());
    }

    public AuthResponse loginAdmin(AdminLoginRequest request) {
        UserAdm admin = userAdmRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new AuthException("Administrador não encontrado."));

        if (!passwordEncoder.matches(request.senha(), admin.getSenha())) {
            throw new AuthException("Senha inválida.");
        }

        String token = jwtService.generateToken(admin.getEmail(), admin.getRole().name());

        return new AuthResponse(token, admin.getRole().name(), admin.getEmail());
    }
}