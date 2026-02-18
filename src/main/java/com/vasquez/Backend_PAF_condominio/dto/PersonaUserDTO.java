package com.vasquez.Backend_PAF_condominio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class PersonaUserDTO {
    // Datos de Persona
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaNac;
    private Long tipoDocumentoId;
    private String numeroDoc;
    // Datos de Usuario
    private String username;
    private String password;
    private Set<String> roles;

}
