package com.motogo.backend.repository;

import com.motogo.backend.model.Alugueis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AluguelRepository extends JpaRepository<Alugueis, Long> {
}