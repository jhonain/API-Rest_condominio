package com.vasquez.Backend_PAF_condominio.controller;

import com.vasquez.Backend_PAF_condominio.dto.UnidadRequestDTO;
import com.vasquez.Backend_PAF_condominio.entity.Unidad;
import com.vasquez.Backend_PAF_condominio.service.UnidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades")
public class UnidadController {

    @Autowired
    private UnidadService unidadService;


    // Listar unidades por condominio: /api/unidades?condominioId=1
    @GetMapping
    public List<Unidad> listarPorCondominio(
            @RequestParam Long condominioId
    ) {
        return unidadService.findByCondominio(condominioId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Unidad> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(unidadService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Unidad> crear(@RequestBody UnidadRequestDTO request) {
        Unidad creada = unidadService.create(request);
        return ResponseEntity.ok(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Unidad> actualizar(
            @PathVariable Long id,
            @RequestBody UnidadRequestDTO request
    ) {
        Unidad actualizada = unidadService.update(id, request);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        unidadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
