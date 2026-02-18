package com.vasquez.Backend_PAF_condominio.repository;

import com.vasquez.Backend_PAF_condominio.entity.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Page<Persona> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
