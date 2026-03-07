package com.motogo.backend.repository;

import com.motogo.backend.model.UserAdm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UserAdm, Long> {

    Optional<UserAdm> findByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);

}