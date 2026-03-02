package com.vasquez.Backend_PAF_condominio.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "horarios_disponibles")
public class HorarioDisponible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime horaInicio;   // 09:00
    private LocalTime horaFin;      // 14:00

    @ManyToOne
    @JoinColumn(name = "area_comun_id")
    private AreaComun areaComun;
}
