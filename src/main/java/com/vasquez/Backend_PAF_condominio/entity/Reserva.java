package com.vasquez.Backend_PAF_condominio.entity;

import com.vasquez.Backend_PAF_condominio.enums.EstadoReserva;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha = LocalDate.now(); // Día que reserva

    @Enumerated(EnumType.STRING)
    private EstadoReserva estado = EstadoReserva.CONFIRMADA; // Auto confirmada

    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "area_comun_id")
    private AreaComun areaComun;

    @ManyToOne
    @JoinColumn(name = "horario_id")
    private HorarioDisponible horario;

    private LocalDateTime fechaRegistro = LocalDateTime.now() ;
}
