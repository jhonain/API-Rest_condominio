package com.vasquez.Backend_PAF_condominio.controller;

import com.vasquez.Backend_PAF_condominio.entity.Rol;
import com.vasquez.Backend_PAF_condominio.entity.TipoDocumento;
import com.vasquez.Backend_PAF_condominio.service.RolService;
import com.vasquez.Backend_PAF_condominio.service.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tipoDocumentos")
public class TipoDocumentoController {

    @Autowired
    private TipoDocumentoService service;

    @GetMapping
    public Page<TipoDocumento> getAll(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return service.search(search, pageable);
    }

    @GetMapping("/{id}")
    public TipoDocumento getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<TipoDocumento> create(@RequestBody TipoDocumento tipoDocumento) {
        TipoDocumento created = service.create(tipoDocumento);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoDocumento> update(@PathVariable Long id, @RequestBody TipoDocumento tipoDocumento) {
        return ResponseEntity.ok(service.update(id, tipoDocumento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
