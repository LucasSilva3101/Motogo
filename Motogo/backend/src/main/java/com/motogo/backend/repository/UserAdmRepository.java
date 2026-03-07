package com.motogo.backend.repository;

import com.motogo.backend.model.UserAdm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAdmRepository extends JpaRepository<UserAdm, Long> {
    Optional<UserAdm> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}