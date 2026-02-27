package com.vasquez.Backend_PAF_condominio.controller;

import com.vasquez.Backend_PAF_condominio.dto.CondominoRequest;
import com.vasquez.Backend_PAF_condominio.entity.Condomino;
import com.vasquez.Backend_PAF_condominio.service.CondominoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/condominos")
public class CondominoController {


    @Autowired
    private CondominoService condominoService;


    // GET /api/condominos
    @GetMapping
    public List<Condomino> listarTodos() {
        return condominoService.listarTodos();
    }

    // GET /api/condominos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Condomino> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(condominoService.buscarPorId(id));
    }

    // GET /api/condominos/unidad/{unidadId}
    @GetMapping("/unidad/{unidadId}")
    public List<Condomino> listarPorUnidad(@PathVariable Long unidadId) {
        return condominoService.listarPorUnidad(unidadId);
    }

    // GET /api/condominos/persona/{personaId}
    @GetMapping("/persona/{personaId}")
    public List<Condomino> listarPorPersona(@PathVariable Long personaId) {
        return condominoService.listarPorPersona(personaId);
    }

    // POST /api/condominos
    @PostMapping
    public ResponseEntity<Condomino> crear(@RequestBody CondominoRequest request) {
        Condomino creado = condominoService.crear(request);
        return ResponseEntity.ok(creado);
    }

    // PUT /api/condominos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Condomino> actualizar(
            @PathVariable Long id,
            @RequestBody CondominoRequest request
    ) {
        Condomino actualizado = condominoService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);
    }

    // DELETE /api/condominos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        condominoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
