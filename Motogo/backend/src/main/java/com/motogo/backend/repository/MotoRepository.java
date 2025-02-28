package com.motogo.backend.repository;

import com.motogo.backend.model.Motos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoRepository extends JpaRepository<Motos, Long> {
}
