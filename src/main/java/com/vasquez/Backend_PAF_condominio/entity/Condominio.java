package com.vasquez.Backend_PAF_condominio.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "condominio")
public class Condominio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, length = 255)
    private String direccion;

    @Column(name = "total_unidades")
    private Integer totalUnidades;

    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;

    @Column(nullable = false)
    private Boolean estado = true;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaRegis = LocalDate.now();

    @OneToMany(mappedBy = "condominio", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Unidad> unidades = new ArrayList<>();

}
