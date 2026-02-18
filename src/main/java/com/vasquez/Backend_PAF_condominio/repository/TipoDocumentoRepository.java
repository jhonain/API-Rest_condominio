package com.vasquez.Backend_PAF_condominio.repository;

import com.vasquez.Backend_PAF_condominio.entity.TipoDocumento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {

    Page<TipoDocumento> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    @Override
    Optional<TipoDocumento> findById(Long id);
}
