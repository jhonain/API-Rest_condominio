package com.vasquez.Backend_PAF_condominio.dto;

import lombok.Data;

import java.util.List;

@Data
public class DashboardResponseDTO {
    // Info del residente
    private Long personaId;
    private String nombreCompleto;
    private String email;
    private String telefono;
    // Info de la unidad
    private UnidadInfoDTO unidad;

    // Info del condominio
    private CondominioInfoDTO condominio;

    // Áreas comunes del condominio
    private List<AreaComunResponseDTO> areasComunes;

    // Reservas activas del residente
    private List<ReservaResponseDTO> misReservas;

}
