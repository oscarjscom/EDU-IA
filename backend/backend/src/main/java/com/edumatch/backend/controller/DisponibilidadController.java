package com.edumatch.backend.controller;

import com.edumatch.backend.model.Disponibilidad;
import com.edumatch.backend.repository.DisponibilidadRepository;
import com.edumatch.backend.repository.DocenteRepository;
import com.edumatch.backend.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/disponibilidad")
public class DisponibilidadController {

    private final DisponibilidadRepository disponibilidadRepository;
    private final DocenteRepository docenteRepository;
    private final UsuarioRepository usuarioRepository;

    public DisponibilidadController(DisponibilidadRepository disponibilidadRepository,
                                    DocenteRepository docenteRepository,
                                    UsuarioRepository usuarioRepository) {
        this.disponibilidadRepository = disponibilidadRepository;
        this.docenteRepository = docenteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<Disponibilidad>> getDisponibilidad(Principal principal) {
        var usuario = usuarioRepository.findByCorreo(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        var docente = docenteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        return ResponseEntity.ok(disponibilidadRepository.findByDocenteId(docente.getId()));
    }

    @PostMapping
    public ResponseEntity<Disponibilidad> agregar(@RequestBody Disponibilidad disponibilidad,
                                                  Principal principal) {
        var usuario = usuarioRepository.findByCorreo(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        var docente = docenteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        disponibilidad.setDocente(docente);
        return ResponseEntity.ok(disponibilidadRepository.save(disponibilidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        disponibilidadRepository.deleteById(id);
        return ResponseEntity.ok("Eliminado correctamente");
    }
}