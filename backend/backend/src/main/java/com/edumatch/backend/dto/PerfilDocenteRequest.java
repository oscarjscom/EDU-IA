package com.edumatch.backend.dto;

import lombok.Data;

@Data
public class PerfilDocenteRequest {
    private String nombre;
    private String correo;
    private String ubicacion;
    private String biografia;
    private String experiencia;
    private Double tarifaHora;
}