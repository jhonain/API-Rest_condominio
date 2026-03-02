package com.vasquez.Backend_PAF_condominio.controller;

import com.vasquez.Backend_PAF_condominio.dto.ReservaDTO;
import com.vasquez.Backend_PAF_condominio.dto.ReservaResponseDTO;
import com.vasquez.Backend_PAF_condominio.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;


    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(@RequestBody ReservaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservaService.crearReserva(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.obtenerPorId(id));
    }

    @GetMapping("/persona/{personaId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarPorPersona(
            @PathVariable Long personaId) {
        return ResponseEntity.ok(reservaService.listarPorPersona(personaId));
    }

    @GetMapping("/area/{areaComunId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarPorArea(
            @PathVariable Long areaComunId) {
        return ResponseEntity.ok(reservaService.listarPorAreaComun(areaComunId));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelarReserva(id));
    }
}
