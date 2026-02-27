package com.vasquez.Backend_PAF_condominio.controller;

import com.vasquez.Backend_PAF_condominio.entity.Condominio;
import com.vasquez.Backend_PAF_condominio.service.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/condominios")
public class CondominioController {

    @Autowired
    private CondominioService condominioService;


    // LISTAR paginado: /api/condominios?page=0&size=10
    @GetMapping
    public Page<Condominio> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return condominioService.findAll(pageable);
    }

    // OBTENER por id
    @GetMapping("/{id}")
    public ResponseEntity<Condominio> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(condominioService.findById(id));
    }

    // CREAR
    @PostMapping
    public ResponseEntity<Condominio> crear(@RequestBody Condominio condominio) {
        Condominio creado = condominioService.create(condominio);
        return ResponseEntity.ok(creado);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<Condominio> actualizar(
            @PathVariable Long id,
            @RequestBody Condominio condominio
    ) {
        Condominio actualizado = condominioService.update(id, condominio);
        return ResponseEntity.ok(actualizado);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        condominioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
