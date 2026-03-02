package com.vasquez.Backend_PAF_condominio.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class HorarioResponseDTO {
    private Long id;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}
