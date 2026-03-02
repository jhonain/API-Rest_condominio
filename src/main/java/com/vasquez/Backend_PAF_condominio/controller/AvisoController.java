package com.vasquez.Backend_PAF_condominio.controller;

import com.vasquez.Backend_PAF_condominio.entity.Aviso;
import com.vasquez.Backend_PAF_condominio.service.AvisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avisos")
public class AvisoController {

    @Autowired
    private AvisoService service;

    // GET /api/avisos
    @GetMapping
    public ResponseEntity<List<Aviso>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    // GET /api/avisos/importantes
    @GetMapping("/importantes")
    public ResponseEntity<List<Aviso>> importantes() {
        return ResponseEntity.ok(service.findImportantes());
    }

    // GET /api/avisos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Aviso> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // POST /api/avisos
    @PostMapping
    public ResponseEntity<Aviso> crear(@RequestBody Aviso aviso) {
        return ResponseEntity.ok(service.save(aviso));
    }

    // PUT /api/avisos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Aviso> actualizar(@PathVariable Long id, @RequestBody Aviso aviso) {
        return ResponseEntity.ok(service.update(id, aviso));
    }

    // DELETE /api/avisos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
