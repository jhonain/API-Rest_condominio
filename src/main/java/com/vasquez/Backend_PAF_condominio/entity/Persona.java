package com.vasquez.Backend_PAF_condominio.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "persona")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", length = 100)
    private String nombre;
    @Column(name = "apellidos", length = 150)
    private String apellidos;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaNac;
    @Column(unique = true, name = "email", length = 100)
    private String email;
    @Column(name = "telefono", length = 9)
    private String telefono;
    private boolean estado;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaRegis;

    @ManyToOne
    @JoinColumn(name = "tipo_documento_id")
    private TipoDocumento tipoDocumento;

    @Column(name = "numeroDocumento", length = 30)
    private String numeroDocumento;

}
