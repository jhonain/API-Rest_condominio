package com.vasquez.Backend_PAF_condominio.dto;

import lombok.Data;

@Data
public class CondominioInfoDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private Integer totalUnidades;
    private String imagenUrl;
}
