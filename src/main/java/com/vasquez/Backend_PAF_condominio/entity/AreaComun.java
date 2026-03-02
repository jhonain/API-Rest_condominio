package com.vasquez.Backend_PAF_condominio.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vasquez.Backend_PAF_condominio.enums.EstadoAreaComun;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "areas_comunes")
public class AreaComun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;        // "Gimnasio", "Salón de Eventos"
    private String descripcion;   // "Equipado con máquinas..."
    private Integer capacidad;    // 50, 15, 30...
    @Enumerated(EnumType.STRING)
    private EstadoAreaComun estado = EstadoAreaComun.DISPONIBLE;

    private String imagenUrl;

    // FK hacia Condominio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condominio_id", nullable = false)
    private Condominio condominio;

    @OneToMany(mappedBy = "areaComun", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioDisponible> horarios = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

}
