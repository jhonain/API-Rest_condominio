package com.vasquez.Backend_PAF_condominio.repository;

import com.vasquez.Backend_PAF_condominio.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario ,Long> {
    Optional<Usuario> findByUsername(String username);
}
