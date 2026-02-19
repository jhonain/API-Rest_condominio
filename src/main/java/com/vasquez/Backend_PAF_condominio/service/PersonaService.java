package com.vasquez.Backend_PAF_condominio.service;

import com.vasquez.Backend_PAF_condominio.dto.PersonaUserDTO;
import com.vasquez.Backend_PAF_condominio.entity.Persona;
import com.vasquez.Backend_PAF_condominio.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonaService {
    Page<Persona> findAll(Pageable pageable);
    Page<Persona> search(String texto, Pageable pageable);
    Persona findById(Long id);
    Persona update(Long id, Persona per);
    Persona create(Persona per);
    void deleteById(Long id);

    // para guardar datos completos para la persona con usuario, rol, tipoDoc
    void guardarPersonaUser(PersonaUserDTO dto);
    void actualizarPersonaUser(Long id, PersonaUserDTO dto );

    //para hacer el registro por medio de gloogleee nada mas
    Usuario findOrCreateByGoogle(String email, String nombre, String apellidos);

}
