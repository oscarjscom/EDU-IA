package com.edumatch.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "sesiones")
@Data
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;

    @Column(nullable = false)
    private String materia;

    private String tema;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    private String plataforma;

    @Column(name = "link_reunion")
    private String linkReunion;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.PROGRAMADA;

    private String notas;

    public enum Estado {
        PROGRAMADA, COMPLETADA, CANCELADA
    }
}