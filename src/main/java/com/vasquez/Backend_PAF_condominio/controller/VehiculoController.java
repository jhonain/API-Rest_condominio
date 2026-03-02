package com.vasquez.Backend_PAF_condominio.controller;

import com.vasquez.Backend_PAF_condominio.dto.VehiculoRequestDTO;
import com.vasquez.Backend_PAF_condominio.dto.VehiculoResponseDTO;
import com.vasquez.Backend_PAF_condominio.entity.Vehiculo;
import com.vasquez.Backend_PAF_condominio.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {


    @Autowired
    private VehiculoService vehiculoService;

    // ─── LISTAR TODOS (Admin/Seguridad) ──────────────────────────
    @GetMapping
    public ResponseEntity<List<VehiculoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(vehiculoService.listarTodos());
    }

    // ─── LISTAR POR PERSONA (Residente ve los suyos) ─────────────
    @GetMapping("/persona/{personaId}")
    public ResponseEntity<List<VehiculoResponseDTO>> listarPorPersona(@PathVariable Long personaId) {
        return ResponseEntity.ok(vehiculoService.listarPorPersona(personaId));
    }

    // ─── BUSCAR POR ID ───────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculoService.buscarPorId(id));
    }

    // ─── REGISTRAR (Residente) ───────────────────────────────────
    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> registrar(@RequestBody VehiculoRequestDTO request) {
        return ResponseEntity.ok(vehiculoService.registrar(request));
    }

    // ─── ACTUALIZAR ──────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody VehiculoRequestDTO request) {
        return ResponseEntity.ok(vehiculoService.actualizar(id, request));
    }

    // ─── ELIMINAR (baja lógica) ───────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
