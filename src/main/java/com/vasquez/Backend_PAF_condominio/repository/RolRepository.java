package com.vasquez.Backend_PAF_condominio.repository;

import com.vasquez.Backend_PAF_condominio.entity.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Page<Rol> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    Optional<Rol> findByNombre(String nombre);
}
