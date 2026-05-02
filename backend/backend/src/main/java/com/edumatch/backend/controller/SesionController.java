package com.edumatch.backend.controller;

import com.edumatch.backend.model.Sesion;
import com.edumatch.backend.repository.DocenteRepository;
import com.edumatch.backend.repository.EstudianteRepository;
import com.edumatch.backend.repository.SesionRepository;
import com.edumatch.backend.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/sesiones")
public class SesionController {

    private final SesionRepository sesionRepository;
    private final EstudianteRepository estudianteRepository;
    private final DocenteRepository docenteRepository;
    private final UsuarioRepository usuarioRepository;

    public SesionController(SesionRepository sesionRepository,
                            EstudianteRepository estudianteRepository,
                            DocenteRepository docenteRepository,
                            UsuarioRepository usuarioRepository) {
        this.sesionRepository = sesionRepository;
        this.estudianteRepository = estudianteRepository;
        this.docenteRepository = docenteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/estudiante")
    public ResponseEntity<List<Sesion>> getSesionesEstudiante(Principal principal) {
        var usuario = usuarioRepository.findByCorreo(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        var estudiante = estudianteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        return ResponseEntity.ok(sesionRepository.findByEstudianteId(estudiante.getId()));
    }

    @GetMapping("/docente")
    public ResponseEntity<List<Sesion>> getSesionesDocente(Principal principal) {
        var usuario = usuarioRepository.findByCorreo(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        var docente = docenteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        return ResponseEntity.ok(sesionRepository.findByDocenteId(docente.getId()));
    }

    @PostMapping
    public ResponseEntity<Sesion> crearSesion(@RequestBody Sesion sesion) {
        return ResponseEntity.ok(sesionRepository.save(sesion));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Sesion> actualizarEstado(@PathVariable Long id,
                                                   @RequestParam Sesion.Estado estado) {
        var sesion = sesionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));
        sesion.setEstado(estado);
        return ResponseEntity.ok(sesionRepository.save(sesion));
    }

    @PutMapping("/{id}/notas")
    public ResponseEntity<Sesion> agregarNotas(@PathVariable Long id,
                                               @RequestParam String notas) {
        var sesion = sesionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));
        sesion.setNotas(notas);
        return ResponseEntity.ok(sesionRepository.save(sesion));
    }
}