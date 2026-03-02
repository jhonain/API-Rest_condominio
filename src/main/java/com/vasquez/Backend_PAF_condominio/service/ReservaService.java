package com.vasquez.Backend_PAF_condominio.service;

import com.vasquez.Backend_PAF_condominio.dto.ReservaDTO;
import com.vasquez.Backend_PAF_condominio.dto.ReservaResponseDTO;

import java.util.List;

public interface ReservaService {
    ReservaResponseDTO crearReserva(ReservaDTO dto);
    ReservaResponseDTO obtenerPorId(Long id);
    List<ReservaResponseDTO> listarPorPersona(Long personaId);
    List<ReservaResponseDTO> listarPorAreaComun(Long areaComunId);
    ReservaResponseDTO cancelarReserva(Long id);
    void finalizarReservasVencidas(); // @Scheduled
}
