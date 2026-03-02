package com.vasquez.Backend_PAF_condominio.repository;

import com.vasquez.Backend_PAF_condominio.entity.Cuota;
import com.vasquez.Backend_PAF_condominio.enums.EstadoCuota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CuotaRepository extends JpaRepository<Cuota, Long> {

    List<Cuota> findByCondominoId(Long condominoId);
    boolean existsByCondominoIdAndFechaVencimiento(Long condominoId, LocalDate fechaVencimiento);
    int countByCondominoId(Long condominoId);

    // Para actualizar vencidas
    List<Cuota> findByEstadoAndFechaVencimientoBefore(EstadoCuota estado, LocalDate fecha);


}
