package com.motogo.backend.service;

import com.motogo.backend.model.UserAdm;
import com.motogo.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserAdm userAdm = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("usuario nao encontrado"));

        return User.builder()
                .username(userAdm.getEmail())
                .password(userAdm.getPassword())
                .roles(userAdm.getRole().name().replace("ROLE_", ""))
                .build();
    }
}