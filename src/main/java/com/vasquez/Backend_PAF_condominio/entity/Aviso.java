package com.vasquez.Backend_PAF_condominio.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "avisos")
public class Aviso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descripcion;

    private String TipoAviso;

    private Boolean importante;

}
