package com.vasquez.Backend_PAF_condominio.service;

import com.vasquez.Backend_PAF_condominio.dto.DashboardResponseDTO;

public interface DashboardService {

    DashboardResponseDTO getDashboardResidente(Long personaId);
}
