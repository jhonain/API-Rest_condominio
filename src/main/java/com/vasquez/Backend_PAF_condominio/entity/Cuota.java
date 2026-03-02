package com.vasquez.Backend_PAF_condominio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vasquez.Backend_PAF_condominio.enums.EstadoCuota;
import com.vasquez.Backend_PAF_condominio.enums.MetodoPago;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "cuotas")
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "condomino_id", nullable = false)
    @JsonIgnore
    private Condomino condomino;

    private Integer numeroCuota;        // 1, 2, 3...
    private BigDecimal monto;           // precio de la unidad al momento de asignar

    private LocalDate fechaVencimiento; // fecha_inicio + N meses
    private LocalDate fechaPago;        // null si no ha pagado

    @Enumerated(EnumType.STRING)
    private EstadoCuota estado;         // PENDIENTE, PAGADA, VENCIDA

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;          // EFECTIVO, TRANSFERENCIA, etc.
    private String observacion;

    private LocalDate fechaRegistro;
}
