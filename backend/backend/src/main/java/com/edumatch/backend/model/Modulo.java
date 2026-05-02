package com.edumatch.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "modulos")
@Data
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ruta_id", nullable = false)
    private RutaAprendizaje ruta;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(name = "horas_estimadas")
    private Integer horasEstimadas;

    private Integer orden;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.PENDIENTE;

    public enum Estado {
        PENDIENTE, EN_PROGRESO, COMPLETADO
    }
}