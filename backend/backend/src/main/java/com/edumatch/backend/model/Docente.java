package com.edumatch.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "docentes")
@Data
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private String biografia;

    private String ubicacion;

    @Column(name = "tarifa_hora")
    private Double tarifaHora;

    private String experiencia;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(name = "rating_promedio")
    private Double ratingPromedio = 0.0;

    @Column(name = "total_sesiones")
    private Integer totalSesiones = 0;

    private boolean verificado = false;
}