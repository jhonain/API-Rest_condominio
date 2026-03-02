package com.vasquez.Backend_PAF_condominio.dto;

import com.vasquez.Backend_PAF_condominio.enums.EstadoReserva;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ReservaResponseDTO {
    private Long id;
    private LocalDate fecha;
    private EstadoReserva estado;
    private LocalDateTime fechaRegistro;

    // Persona
    private Long personaId;
    private String personaNombre;

    // Área común
    private Long areaComunId;
    private String areaComunNombre;

    // Horario
    private Long horarioId;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;
}