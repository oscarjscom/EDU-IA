package com.edumatch.backend.dto;

import lombok.Data;

@Data
public class PerfilEstudianteRequest {
    private String nombre;
    private String correo;
    private String ubicacion;
    private String nivel;
    private String objetivos;
    private String horasDisponibles;
    private String horarioPreferido;
    private String duracionSesion;
}