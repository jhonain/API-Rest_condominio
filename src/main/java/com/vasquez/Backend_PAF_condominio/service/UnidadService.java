package com.vasquez.Backend_PAF_condominio.service;

import com.vasquez.Backend_PAF_condominio.dto.UnidadRequestDTO;
import com.vasquez.Backend_PAF_condominio.entity.Unidad;

import java.util.List;

public interface UnidadService {

    List<Unidad> findByCondominio(Long condominioId);

    Unidad findById(Long id);

    Unidad create(UnidadRequestDTO request);

    Unidad update(Long id, UnidadRequestDTO request);

    void deleteById(Long id);
}
