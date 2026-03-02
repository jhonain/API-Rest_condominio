package com.vasquez.Backend_PAF_condominio.service;

import com.vasquez.Backend_PAF_condominio.dto.VehiculoRequestDTO;
import com.vasquez.Backend_PAF_condominio.dto.VehiculoResponseDTO;
import com.vasquez.Backend_PAF_condominio.entity.Vehiculo;

import java.util.List;

public interface VehiculoService {

    List<VehiculoResponseDTO> listarTodos();
    List<VehiculoResponseDTO> listarPorPersona(Long personaId);
    VehiculoResponseDTO buscarPorId(Long id);
    VehiculoResponseDTO registrar(VehiculoRequestDTO request);
    VehiculoResponseDTO actualizar(Long id, VehiculoRequestDTO request);
    void eliminar(Long id);

}
