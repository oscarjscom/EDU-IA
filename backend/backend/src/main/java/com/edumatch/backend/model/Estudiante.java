package com.edumatch.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estudiantes")
@Data
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private String nivel;

    private String objetivos;

    @Column(name = "horas_disponibles")
    private String horasDisponibles;

    @Column(name = "horario_preferido")
    private String horarioPreferido;

    @Column(name = "duracion_sesion")
    private String duracionSesion;

    @Column(name = "foto_url")
    private String fotoUrl;

    private String ubicacion;
}