package com.vasquez.Backend_PAF_condominio.repository;

import com.vasquez.Backend_PAF_condominio.entity.AreaComun;
import com.vasquez.Backend_PAF_condominio.enums.EstadoAreaComun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaComunRepository extends JpaRepository<AreaComun, Long> {

    // Áreas de un condominio específico
    List<AreaComun> findByCondominioId(Long condominioId);

    // Reemplaza el anterior findByCondominioIdAndDisponibleTrue
    List<AreaComun> findByCondominioIdAndEstado(Long condominioId, EstadoAreaComun estado);
}
