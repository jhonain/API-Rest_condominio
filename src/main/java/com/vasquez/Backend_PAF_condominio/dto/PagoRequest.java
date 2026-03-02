package com.vasquez.Backend_PAF_condominio.dto;

import com.vasquez.Backend_PAF_condominio.enums.MetodoPago;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
;

@Data
public class PagoRequest {
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;   // EFECTIVO, TRANSFERENCIA, etc.
    private String observacion;
}