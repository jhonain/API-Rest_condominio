package com.vasquez.Backend_PAF_condominio.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaDTO {
    private LocalDate fecha;
    private Long personaId;
    private Long areaComunId;
    private Long horarioId;
}
