package com.vasquez.Backend_PAF_condominio.controller;

import com.vasquez.Backend_PAF_condominio.dto.AreaComunResponseDTO;
import com.vasquez.Backend_PAF_condominio.entity.AreaComun;
import com.vasquez.Backend_PAF_condominio.service.AreaComunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas-comunes")
public class AreaComunController {

    @Autowired
    private AreaComunService areaComunService;

    // ✅ POST - Crear área común con horarios
    @PostMapping("/condominio/{condominioId}")
    public ResponseEntity<AreaComunResponseDTO> crear(
            @PathVariable Long condominioId,
            @RequestBody AreaComunResponseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(areaComunService.crearAreaComun(dto, condominioId));
    }

    // ✅ GET - Listar todas las áreas de un condominio
    @GetMapping("/condominio/{condominioId}")
    public ResponseEntity<List<AreaComunResponseDTO>> listar(
            @PathVariable Long condominioId) {
        return ResponseEntity.ok(areaComunService.listarPorCondominio(condominioId));
    }

    // ✅ GET - Listar solo áreas DISPONIBLES de un condominio
    @GetMapping("/condominio/{condominioId}/disponibles")
    public ResponseEntity<List<AreaComunResponseDTO>> listarDisponibles(
            @PathVariable Long condominioId) {
        return ResponseEntity.ok(areaComunService.listarDisponiblesPorCondominio(condominioId));
    }

    // ✅ GET - Obtener área por ID
    @GetMapping("/{id}")
    public ResponseEntity<AreaComun> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(areaComunService.obtenerPorId(id));
    }


    // GET para mostrar todos
    @GetMapping
    public ResponseEntity<List<AreaComunResponseDTO>> listar() {
        return ResponseEntity.ok(areaComunService.listarTodo());
    }

    // ✅ PUT - Actualizar área común
    @PutMapping("/{id}")
    public ResponseEntity<AreaComunResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody AreaComunResponseDTO dto) {
        return ResponseEntity.ok(areaComunService.actualizarAreaComun(id, dto));
    }

    // ✅ PATCH - Cambiar estado manual (admin)
    /*@PatchMapping("/{id}/estado")
    public ResponseEntity<AreaComun> cambiarEstado(
            @PathVariable Long id,
            @RequestParam EstadoAreaComun estado) {
        return ResponseEntity.ok(areaComunService.cambiarEstadoManual(id, estado));
    }*/

    // ✅ DELETE - Eliminar área común
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        areaComunService.eliminarAreaComun(id);
        return ResponseEntity.noContent().build();
    }

}
