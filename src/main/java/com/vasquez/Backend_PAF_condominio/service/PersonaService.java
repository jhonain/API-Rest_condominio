package com.vasquez.Backend_PAF_condominio.service;

import com.vasquez.Backend_PAF_condominio.dto.PersonaUserDTO;
import com.vasquez.Backend_PAF_condominio.entity.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonaService {
    Page<Persona> findAll(Pageable pageable);
    Page<Persona> search(String texto, Pageable pageable);
    Persona findById(Long id);
    Persona update(Long id, Persona per);
    Persona create(Persona per);
    void deleteById(Long id);

    void guardarPersonaUser(PersonaUserDTO dto);
    void actualizarPersonaUser(Long id, PersonaUserDTO dto );
}
