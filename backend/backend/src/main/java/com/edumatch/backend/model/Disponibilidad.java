package com.edumatch.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "disponibilidad")
@Data
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;

    private String dia;

    @Column(name = "hora_inicio")
    private String horaInicio;

    @Column(name = "hora_fin")
    private String horaFin;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.DISPONIBLE;

    public enum Estado {
        DISPONIBLE, BLOQUEADO
    }
}