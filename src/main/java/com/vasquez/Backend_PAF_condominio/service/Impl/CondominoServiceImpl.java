package com.vasquez.Backend_PAF_condominio.service.Impl;

import com.vasquez.Backend_PAF_condominio.dto.CondominoRequest;
import com.vasquez.Backend_PAF_condominio.entity.Condomino;
import com.vasquez.Backend_PAF_condominio.entity.Persona;
import com.vasquez.Backend_PAF_condominio.entity.Unidad;
import com.vasquez.Backend_PAF_condominio.repository.CondominoRepository;
import com.vasquez.Backend_PAF_condominio.repository.PersonaRepository;
import com.vasquez.Backend_PAF_condominio.repository.UnidadRepository;
import com.vasquez.Backend_PAF_condominio.service.CondominoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CondominoServiceImpl implements CondominoService {

    @Autowired
    private CondominoRepository condominoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UnidadRepository unidadRepository;

    @Override
    public List<Condomino> listarTodos() {
        return condominoRepository.findAll();
    }

    @Override
    public List<Condomino> listarPorUnidad(Long unidadId) {
        return condominoRepository.findByUnidadId(unidadId);
    }

    @Override
    public List<Condomino> listarPorPersona(Long personaId) {
        return condominoRepository.findByPersonaId(personaId);
    }

    @Override
    public Condomino buscarPorId(Long id) {
        return condominoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Condomino no encontrado"));
    }

    @Override
    public Condomino crear(CondominoRequest r) {
        Persona persona = personaRepository.findById(r.getPersonaId())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        Unidad unidad = unidadRepository.findById(r.getUnidadId())
                .orElseThrow(() -> new RuntimeException("Unidad no encontrada"));

        unidad.setEstado("OCUPADO");
        Condomino c = new Condomino();
        c.setPersona(persona);
        c.setUnidad(unidad);
        c.setRol(r.getRol());
        c.setFechaInicio(r.getFechaInicio());
        c.setFechaFin(r.getFechaFin());
        c.setEstado(r.getEstado() != null ? r.getEstado() : true);

        return condominoRepository.save(c);
    }

    @Override
    public Condomino actualizar(Long id, CondominoRequest r) {
        Condomino c = buscarPorId(id);

        if (r.getPersonaId() != null) {
            Persona persona = personaRepository.findById(r.getPersonaId())
                    .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
            c.setPersona(persona);
        }

        if (r.getUnidadId() != null) {
            Unidad unidad = unidadRepository.findById(r.getUnidadId())
                    .orElseThrow(() -> new RuntimeException("Unidad no encontrada"));
            c.setUnidad(unidad);
        }

        c.setRol(r.getRol());
        c.setFechaInicio(r.getFechaInicio());
        c.setFechaFin(r.getFechaFin());
        if (r.getEstado() != null) {
            c.setEstado(r.getEstado());
        }

        return condominoRepository.save(c);
    }

    @Override
    public void eliminar(Long id) {
        Condomino c = buscarPorId(id);
        Unidad u = c.getUnidad();
        u.setEstado("DISPONIBLE");
        unidadRepository.save(u);
        condominoRepository.deleteById(id);
    }

}
