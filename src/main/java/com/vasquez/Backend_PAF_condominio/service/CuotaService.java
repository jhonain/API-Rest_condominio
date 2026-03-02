package com.vasquez.Backend_PAF_condominio.service;

import com.vasquez.Backend_PAF_condominio.dto.CuotaResponseDTO;
import com.vasquez.Backend_PAF_condominio.dto.PagoRequest;
import com.vasquez.Backend_PAF_condominio.entity.Cuota;

import java.util.List;

public interface CuotaService {

    List<Cuota> listarTodas();
    List<Cuota> listarPorCondomino(Long condominoId);
    Cuota buscarPorId(Long id);
    Cuota registrarPago(Long id, PagoRequest request);
    void marcarVencidas();
    List<Cuota> listarMisCuotas(Long personaId);

    List<CuotaResponseDTO> listarTodasConResidente();

}
