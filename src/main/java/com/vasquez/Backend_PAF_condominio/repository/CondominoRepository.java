package com.vasquez.Backend_PAF_condominio.repository;

import com.vasquez.Backend_PAF_condominio.entity.Condomino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CondominoRepository extends JpaRepository<Condomino, Long> {
    // listar condominos por unidad
    List<Condomino> findByUnidadId(Long unidadId);

    // listar condominos por persona
    List<Condomino> findByPersonaId(Long personaId);
}
