package com.vasquez.Backend_PAF_condominio.mapper;

import com.vasquez.Backend_PAF_condominio.dto.ReservaResponseDTO;
import com.vasquez.Backend_PAF_condominio.entity.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {

    public ReservaResponseDTO ToDTO(Reserva reserva) {
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.setId(reserva.getId());
        dto.setFecha(reserva.getFecha());
        dto.setEstado(reserva.getEstado());
        dto.setFechaRegistro(reserva.getFechaRegistro());

        dto.setPersonaId(reserva.getPersona().getId());
        dto.setPersonaNombre(reserva.getPersona().getNombre()
                + " " + reserva.getPersona().getApellidos());

        dto.setAreaComunId(reserva.getAreaComun().getId());
        dto.setAreaComunNombre(reserva.getAreaComun().getNombre());

        dto.setHorarioId(reserva.getHorario().getId());
        dto.setHorarioInicio(reserva.getHorario().getHoraInicio());
        dto.setHorarioFin(reserva.getHorario().getHoraFin());

        return dto;
    }
}
