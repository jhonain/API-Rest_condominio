package com.vasquez.Backend_PAF_condominio.mapper;

import com.vasquez.Backend_PAF_condominio.dto.VehiculoResponseDTO;
import com.vasquez.Backend_PAF_condominio.entity.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class VehiculoMapper {

    public VehiculoResponseDTO toResponse(Vehiculo v) {
        VehiculoResponseDTO dto = new VehiculoResponseDTO();
        dto.setId(v.getId());
        dto.setTipo(v.getTipo());
        dto.setMarca(v.getMarca());
        dto.setModelo(v.getModelo());
        dto.setAnio(v.getAnio());
        dto.setColor(v.getColor());
        dto.setPlaca(v.getPlaca());
        dto.setEstado(v.getEstado());
        dto.setPersonaId(v.getPersona().getId());
        dto.setNombre(v.getPersona().getNombre());
        dto.setApellidos(v.getPersona().getApellidos());
        return dto;
    }
}
