package com.vasquez.Backend_PAF_condominio.entity;

import com.vasquez.Backend_PAF_condominio.enums.TipoVehiculo;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @Enumerated(EnumType.STRING)
    private TipoVehiculo tipo;      // AUTO, MOTO, CAMIONETA

    private String marca;           // Honda, Toyota...
    private String modelo;          // Civic, RAV4...
    private Integer anio;           // 2024
    private String color;           // Blanco

    @Column(unique = true, nullable = false)
    private String placa;           // ABC-1234

    private Boolean estado = true;

}
