package com.vasquez.Backend_PAF_condominio.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class UnidadRequestDTO {
    private Long condominioId;
    private String codigo;
    private Integer piso;
    private BigDecimal areaM2;
    private BigDecimal precioMensual;
    private String imagenUrl;
    private String estado; // opcional, por defecto DISPONIBLE
}
