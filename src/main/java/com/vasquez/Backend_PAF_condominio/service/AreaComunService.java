package com.vasquez.Backend_PAF_condominio.service;

import com.vasquez.Backend_PAF_condominio.dto.AreaComunResponseDTO;
import com.vasquez.Backend_PAF_condominio.entity.AreaComun;
import com.vasquez.Backend_PAF_condominio.enums.EstadoAreaComun;

import java.util.List;

public interface AreaComunService {
    AreaComunResponseDTO  crearAreaComun(AreaComunResponseDTO dto, Long condominioId);
    List<AreaComunResponseDTO > listarPorCondominio(Long condominioId);
    List<AreaComunResponseDTO> listarDisponiblesPorCondominio(Long condominioId);
    AreaComun  obtenerPorId(Long id);
    AreaComunResponseDTO  actualizarAreaComun(Long id, AreaComunResponseDTO dto);
    void eliminarAreaComun(Long id);

    List<AreaComunResponseDTO > listarTodo();
    // para manejar el estado de las areassss
    void verificarYActualizarEstado(AreaComun area);
    AreaComun cambiarEstadoManual(Long id, EstadoAreaComun estado);
}
