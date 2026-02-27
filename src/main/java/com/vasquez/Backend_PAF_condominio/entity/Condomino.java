package com.vasquez.Backend_PAF_condominio.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Data
@Entity
@Table(name = "condomino")
public class Condomino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Muchas relaciones condomino apuntan a una persona
    // IMPORTANTE: sin cascade REMOVE para no borrar personas
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    // Muchas relaciones condomino apuntan a una unidad
    // Tampoco ponemos cascade REMOVE aquí
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidad_id", nullable = false)
    private Unidad unidad;

    @Column(nullable = false, length = 30)
    private String rol;  // PROPIETARIO / INQUILINO / OTRO

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(nullable = false)
    private Boolean estado = true;


}
