package com.edumatch.backend.controller;

import com.edumatch.backend.model.Estudiante;
import com.edumatch.backend.repository.EstudianteRepository;
import com.edumatch.backend.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/estudiante")
public class EstudianteController {

    private final EstudianteRepository estudianteRepository;
    private final UsuarioRepository usuarioRepository;

    public EstudianteController(EstudianteRepository estudianteRepository,
                                UsuarioRepository usuarioRepository) {
        this.estudianteRepository = estudianteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/perfil")
    public ResponseEntity<?> getPerfil(Principal principal) {
        var usuario = usuarioRepository.findByCorreo(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        var estudiante = estudianteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        return ResponseEntity.ok(estudiante);
    }

    @PostMapping("/perfil")
    public ResponseEntity<?> crearPerfil(@RequestBody Estudiante estudiante,
                                         Principal principal) {
        var usuario = usuarioRepository.findByCorreo(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        estudiante.setUsuario(usuario);
        return ResponseEntity.ok(estudianteRepository.save(estudiante));
    }

    @PutMapping("/perfil")
    public ResponseEntity<?> actualizarPerfil(@RequestBody Estudiante datos,
                                              Principal principal) {
        var usuario = usuarioRepository.findByCorreo(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // Upsert: si no existe el perfil todavía, lo crea
        var estudiante = estudianteRepository.findByUsuarioId(usuario.getId())
                .orElse(new Estudiante());
        estudiante.setUsuario(usuario);
        estudiante.setNivel(datos.getNivel());
        estudiante.setObjetivos(datos.getObjetivos());
        estudiante.setHorasDisponibles(datos.getHorasDisponibles());
        estudiante.setHorarioPreferido(datos.getHorarioPreferido());
        if (datos.getUbicacion() != null) estudiante.setUbicacion(datos.getUbicacion());
        return ResponseEntity.ok(estudianteRepository.save(estudiante));
    }
}