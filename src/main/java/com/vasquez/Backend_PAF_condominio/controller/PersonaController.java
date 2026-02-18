package com.vasquez.Backend_PAF_condominio.controller;

import com.vasquez.Backend_PAF_condominio.dto.PersonaUserDTO;
import com.vasquez.Backend_PAF_condominio.entity.Persona;
import com.vasquez.Backend_PAF_condominio.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaService service;

    @GetMapping

    public Page<Persona> mostrarr(@PageableDefault(size = 10, sort = "id") Pageable pageable){
        return service.findAll(pageable);
    }

    @GetMapping("/buscar/{id}")
    public Page<Persona> mostrarPersona(
            @RequestParam(required = false) String search,
            @PageableDefault(size =10, sort = "id") Pageable pageable
    ){
        return service.search(search, pageable);
    }

    @GetMapping("/{id}")
    public Persona getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Persona> create(@RequestBody Persona persona) {
        Persona created = service.create(persona);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> update(@PathVariable Long id, @RequestBody Persona persona) {
        return ResponseEntity.ok(service.update(id, persona));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/personaUser")
    public ResponseEntity<String>registrar(@RequestBody PersonaUserDTO dto) {
        service.guardarPersonaUser(dto);
        return ResponseEntity.ok("Residente y usuario creados exitosamente");
    }


    @PutMapping("/personaUser/{usuarioId}")
    public ResponseEntity<String> actualizar(
            @PathVariable Long usuarioId,
            @RequestBody PersonaUserDTO dto
    ) {
        service.actualizarPersonaUser(usuarioId, dto);
        return ResponseEntity.ok("Residente y usuario actualizados correctamente");
    }
}
