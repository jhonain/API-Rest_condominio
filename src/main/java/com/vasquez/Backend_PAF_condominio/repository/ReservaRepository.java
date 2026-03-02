package com.vasquez.Backend_PAF_condominio.repository;

import com.vasquez.Backend_PAF_condominio.entity.Reserva;
import com.vasquez.Backend_PAF_condominio.enums.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Contar reservas confirmadas por área+horario+fecha (validación Opción B)
    long countByAreaComunIdAndHorarioIdAndFechaAndEstado(
            Long areaComunId, Long horarioId, LocalDate fecha, EstadoReserva estado
    );

    // Listar reservas de un residente
    List<Reserva> findByPersonaId(Long personaId);

    // Listar reservas de un área
    List<Reserva> findByAreaComunId(Long areaComunId);

    // Para verificarYActualizarEstado en AreaComunService
    long countByAreaComunIdAndEstado(Long areaComunId, EstadoReserva estado);

    List<Reserva> findByEstado(EstadoReserva estado);


    List<Reserva> findByPersonaIdAndEstado(Long personaId, EstadoReserva estado);

}
