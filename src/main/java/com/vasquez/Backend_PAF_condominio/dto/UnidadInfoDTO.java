package com.vasquez.Backend_PAF_condominio.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UnidadInfoDTO {
    private Long id;
    private String codigo;
    private Integer piso;
    private BigDecimal areaM2;
    private BigDecimal precioMensual;
    private String estado;
    private String imagenUrl;
}
