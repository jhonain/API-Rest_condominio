package com.vasquez.Backend_PAF_condominio.dto;

import com.vasquez.Backend_PAF_condominio.enums.TipoVehiculo;
import lombok.Data;


@Data
public class VehiculoResponseDTO {

    private Long id;
    private TipoVehiculo tipo;
    private String marca;
    private String modelo;
    private Integer anio;
    private String color;
    private String placa;
    private Boolean estado;

    // Solo datos básicos de la persona
    private Long personaId;
    private String nombre;
    private String apellidos;
}
