package com.motogo.backend.repository;

import com.motogo.backend.model.Clientes;
import com.motogo.backend.model.Motos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {
}
