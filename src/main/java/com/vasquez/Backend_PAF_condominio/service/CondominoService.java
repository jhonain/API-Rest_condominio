package com.vasquez.Backend_PAF_condominio.service;

import com.vasquez.Backend_PAF_condominio.dto.CondominoRequest;
import com.vasquez.Backend_PAF_condominio.entity.Condomino;

import java.util.List;

public interface CondominoService {

    List<Condomino> listarTodos();
    List<Condomino> listarPorUnidad(Long unidadId);
    List<Condomino> listarPorPersona(Long personaId);
    Condomino buscarPorId(Long id);
    Condomino crear(CondominoRequest request);
    Condomino actualizar(Long id, CondominoRequest request);
    void eliminar(Long id);
}
