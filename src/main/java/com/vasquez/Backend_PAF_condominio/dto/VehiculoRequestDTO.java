package com.vasquez.Backend_PAF_condominio.dto;

import com.vasquez.Backend_PAF_condominio.enums.TipoVehiculo;
import lombok.Data;

@Data
public class VehiculoRequestDTO {

    private Long personaId;

    private TipoVehiculo tipo;
    private String marca;
    private String modelo;
    private Integer anio;
    private String color;
    private String placa;
}
