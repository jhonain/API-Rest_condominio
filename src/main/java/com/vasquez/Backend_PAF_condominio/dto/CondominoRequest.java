package com.vasquez.Backend_PAF_condominio.dto;

// CondominoRequest.java
import lombok.Data;
import java.time.LocalDate;

@Data
public class CondominoRequest {
    private Long personaId;
    private Long unidadId;
    private String rol;          // PROPIETARIO / INQUILINO / OTRO
    private LocalDate fechaInicio = LocalDate.now();
    private LocalDate fechaFin;  // opcional
    private Boolean estado;
}
