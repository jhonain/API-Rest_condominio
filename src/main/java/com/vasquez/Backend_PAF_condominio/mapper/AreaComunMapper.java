package com.vasquez.Backend_PAF_condominio.mapper;

import com.vasquez.Backend_PAF_condominio.dto.AreaComunResponseDTO;
import com.vasquez.Backend_PAF_condominio.dto.HorarioResponseDTO;
import com.vasquez.Backend_PAF_condominio.entity.AreaComun;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AreaComunMapper {

    public AreaComunResponseDTO mapToDTO(AreaComun area) {
        AreaComunResponseDTO dto = new AreaComunResponseDTO();
        dto.setId(area.getId());
        dto.setNombre(area.getNombre());
        dto.setDescripcion(area.getDescripcion());
        dto.setCapacidad(area.getCapacidad());
        dto.setEstado(area.getEstado());
        dto.setFechaRegistro(area.getFechaRegistro());
        dto.setImagenUrl(area.getImagenUrl());
        dto.setCondominioId(area.getCondominio().getId());
        dto.setCondominioNombre(area.getCondominio().getNombre());

        List<HorarioResponseDTO> horarios = area.getHorarios().stream()
                .map(h -> {
                    HorarioResponseDTO hDto = new HorarioResponseDTO();
                    hDto.setId(h.getId());
                    hDto.setHoraInicio(h.getHoraInicio());
                    hDto.setHoraFin(h.getHoraFin());
                    return hDto;
                }).toList();

        dto.setHorarios(horarios);
        return dto;
    }
}
