package com.edumatch.backend.controller;

import com.edumatch.backend.model.Modulo;
import com.edumatch.backend.model.RutaAprendizaje;
import com.edumatch.backend.repository.EstudianteRepository;
import com.edumatch.backend.repository.ModuloRepository;
import com.edumatch.backend.repository.RutaAprendizajeRepository;
import com.edumatch.backend.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/ruta")
public class RutaAprendizajeController {

    private final RutaAprendizajeRepository rutaRepository;
    private final ModuloRepository moduloRepository;
    private final EstudianteRepository estudianteRepository;
    private final UsuarioRepository usuarioRepository;

    public RutaAprendizajeController(RutaAprendizajeRepository rutaRepository,
                                     ModuloRepository moduloRepository,
                                     EstudianteRepository estudianteRepository,
                                     UsuarioRepository usuarioRepository) {
        this.rutaRepository = rutaRepository;
        this.moduloRepository = moduloRepository;
        this.estudianteRepository = estudianteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/activa")
    public ResponseEntity<?> getRutaActiva(Principal principal) {
        var usuario = usuarioRepository.findByCorreo(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        var estudiante = estudianteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        var ruta = rutaRepository.findByEstudianteIdAndActivaTrue(estudiante.getId())
                .orElseThrow(() -> new RuntimeException("No hay ruta activa"));
        return ResponseEntity.ok(ruta);
    }

    @PostMapping
    public ResponseEntity<RutaAprendizaje> crearRuta(
            @RequestBody RutaAprendizaje ruta, Principal principal) {
        var usuario = usuarioRepository.findByCorreo(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        var estudiante = estudianteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        ruta.setEstudiante(estudiante);
        return ResponseEntity.ok(rutaRepository.save(ruta));
    }

    @GetMapping("/{rutaId}/modulos")
    public ResponseEntity<List<Modulo>> getModulos(@PathVariable Long rutaId) {
        return ResponseEntity.ok(moduloRepository.findByRutaIdOrderByOrden(rutaId));
    }

    @PostMapping("/{rutaId}/modulos")
    public ResponseEntity<Modulo> agregarModulo(@PathVariable Long rutaId,
                                                @RequestBody Modulo modulo) {
        var ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
        modulo.setRuta(ruta);
        return ResponseEntity.ok(moduloRepository.save(modulo));
    }

    @PutMapping("/modulos/{moduloId}/completar")
    public ResponseEntity<Modulo> completarModulo(@PathVariable Long moduloId) {
        var modulo = moduloRepository.findById(moduloId)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));
        modulo.setEstado(Modulo.Estado.COMPLETADO);
        var moduloGuardado = moduloRepository.save(modulo);

        // Actualizar progreso general de la ruta
        var ruta = modulo.getRuta();
        List<Modulo> todos = moduloRepository.findByRutaIdOrderByOrden(ruta.getId());
        long completados = todos.stream()
                .filter(m -> m.getEstado() == Modulo.Estado.COMPLETADO)
                .count();
        int progreso = (int) ((completados * 100) / todos.size());
        ruta.setProgresoGeneral(progreso);
        rutaRepository.save(ruta);

        return ResponseEntity.ok(moduloGuardado);
    }
}