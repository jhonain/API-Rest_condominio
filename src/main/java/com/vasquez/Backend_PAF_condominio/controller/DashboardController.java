package com.vasquez.Backend_PAF_condominio.controller;

import com.vasquez.Backend_PAF_condominio.dto.DashboardResponseDTO;
import com.vasquez.Backend_PAF_condominio.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/residente/{personaId}")
    public ResponseEntity<DashboardResponseDTO> getDashboard(
            @PathVariable Long personaId) {
        return ResponseEntity.ok(dashboardService.getDashboardResidente(personaId));
    }
}
