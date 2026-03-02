package com.vasquez.Backend_PAF_condominio.service.Impl;

import com.vasquez.Backend_PAF_condominio.dto.VehiculoRequestDTO;
import com.vasquez.Backend_PAF_condominio.dto.VehiculoResponseDTO;
import com.vasquez.Backend_PAF_condominio.entity.Persona;
import com.vasquez.Backend_PAF_condominio.entity.Vehiculo;
import com.vasquez.Backend_PAF_condominio.mapper.VehiculoMapper;
import com.vasquez.Backend_PAF_condominio.repository.PersonaRepository;
import com.vasquez.Backend_PAF_condominio.repository.VehiculoRepository;
import com.vasquez.Backend_PAF_condominio.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private VehiculoMapper vehiculoMapper;

    // ─── LISTAR TODOS ────────────────────────────────────────────
    @Override
    public List<VehiculoResponseDTO> listarTodos() {
        return vehiculoRepository.findAll().stream()
                .map(vehiculoMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ─── LISTAR POR PERSONA ──────────────────────────────────────
    @Override
    public List<VehiculoResponseDTO> listarPorPersona(Long personaId) {
        return vehiculoRepository.findByPersonaId(personaId).stream()
                .map(vehiculoMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ─── BUSCAR POR ID ───────────────────────────────────────────
    @Override
    public VehiculoResponseDTO buscarPorId(Long id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        return vehiculoMapper.toResponse(vehiculo);

    }

    // ─── REGISTRAR ───────────────────────────────────────────────
    @Override
    public VehiculoResponseDTO registrar(VehiculoRequestDTO request) {
        if (vehiculoRepository.existsByPlaca(request.getPlaca())) {
            throw new RuntimeException("Ya existe un vehículo con la placa: " + request.getPlaca());
        }

        Persona persona = personaRepository.findById(request.getPersonaId())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPersona(persona);
        vehiculo.setTipo(request.getTipo());
        vehiculo.setMarca(request.getMarca());
        vehiculo.setModelo(request.getModelo());
        vehiculo.setAnio(request.getAnio());
        vehiculo.setColor(request.getColor());
        vehiculo.setPlaca(request.getPlaca());
        vehiculo.setEstado(true);

        return vehiculoMapper.toResponse(vehiculoRepository.save(vehiculo));
    }

    // ─── ACTUALIZAR ──────────────────────────────────────────────
    @Override
    public VehiculoResponseDTO actualizar(Long id, VehiculoRequestDTO request) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        vehiculo.setTipo(request.getTipo());
        vehiculo.setMarca(request.getMarca());
        vehiculo.setModelo(request.getModelo());
        vehiculo.setAnio(request.getAnio());
        vehiculo.setColor(request.getColor());
        vehiculo.setPlaca(request.getPlaca());

        return vehiculoMapper.toResponse(vehiculoRepository.save(vehiculo));
    }

    // ─── ELIMINAR ────────────────────────────────────────────────
    @Override
    public void eliminar(Long id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        vehiculo.setEstado(false);
        vehiculoRepository.save(vehiculo);
    }

}
