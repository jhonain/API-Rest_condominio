package com.vasquez.Backend_PAF_condominio.dto;

import com.vasquez.Backend_PAF_condominio.enums.EstadoCuota;
import com.vasquez.Backend_PAF_condominio.enums.MetodoPago;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CuotaResponseDTO {
    private Long id;
    private Integer numeroCuota;
    private BigDecimal monto;
    private LocalDate fechaVencimiento;
    private LocalDate fechaPago;
    private LocalDate fechaRegistro;
    private EstadoCuota estado;
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;
    private String observacion;

    // Datos del residente
    private String nombreResidente;
    private String apellidosResidente;
    private String unidadCodigo;
}
