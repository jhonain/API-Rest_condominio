package com.vasquez.Backend_PAF_condominio.dto;

import com.vasquez.Backend_PAF_condominio.enums.EstadoAreaComun;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AreaComunResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer capacidad;
    private EstadoAreaComun estado;
    private LocalDateTime fechaRegistro;
    private String imagenUrl;
    private Long condominioId;        // Solo el ID, no el objeto completo
    private String condominioNombre;  // Solo el nombre
    private List<HorarioResponseDTO> horarios;
}
