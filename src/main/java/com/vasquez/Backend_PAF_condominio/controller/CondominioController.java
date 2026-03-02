package com.vasquez.Backend_PAF_condominio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasquez.Backend_PAF_condominio.entity.Condominio;
import com.vasquez.Backend_PAF_condominio.service.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Condominio> crear(
            @RequestPart("condominio") String condominioJson,   // ← Cambiado a String
            @RequestPart(value = "archivo", required = false) MultipartFile archivo
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Condominio condominio = mapper.readValue(condominioJson, Condominio.class); // ← Parsear manualmente
        return ResponseEntity.ok(condominioService.create(condominio, archivo));
    }


    // ACTUALIZAR
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Condominio> actualizar(
            @PathVariable Long id,
            @RequestPart("condominio") Condominio condominio,
            @RequestPart(value = "archivo", required = false) MultipartFile archivo
    ) throws IOException {
        return ResponseEntity.ok(condominioService.update(id, condominio, archivo));
    }


    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        condominioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // SUBIR FOTO
    @PostMapping("/{id}/foto")
    public ResponseEntity<Condominio> subirFoto(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo
    ) throws IOException {
        Condominio actualizado = condominioService.subirFoto(id, archivo);
        return ResponseEntity.ok(actualizado);
    }
}
