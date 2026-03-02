package com.vasquez.Backend_PAF_condominio.controller;

import com.vasquez.Backend_PAF_condominio.dto.CuotaResponseDTO;
import com.vasquez.Backend_PAF_condominio.dto.PagoRequest;
import com.vasquez.Backend_PAF_condominio.entity.Cuota;
import com.vasquez.Backend_PAF_condominio.service.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuotas")
public class CuotaController {

    @Autowired
    private CuotaService cuotaService;


    // ─── LISTAR TODAS ───────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<CuotaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(cuotaService.listarTodasConResidente());
    }


    // ─── LISTAR POR CONDOMINO ────────────────────────────────────
    @GetMapping("/condomino/{condominoId}")
    public ResponseEntity<List<Cuota>> listarPorCondomino(@PathVariable Long condominoId) {
        return ResponseEntity.ok(cuotaService.listarPorCondomino(condominoId));
    }

    @GetMapping("/mis-cuotas/{personaId}")
    public ResponseEntity<List<Cuota>> misCuotas(@PathVariable Long personaId) {
        return ResponseEntity.ok(cuotaService.listarMisCuotas(personaId));
    }

    // ─── BUSCAR POR ID ───────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<Cuota> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cuotaService.buscarPorId(id));
    }

    // ─── REGISTRAR PAGO ─────────────────────────────────────────
    @PatchMapping("/{id}/pagar")
    public ResponseEntity<Cuota> registrarPago(
            @PathVariable Long id,
            @RequestBody PagoRequest request) {
        return ResponseEntity.ok(cuotaService.registrarPago(id, request));
    }

}
