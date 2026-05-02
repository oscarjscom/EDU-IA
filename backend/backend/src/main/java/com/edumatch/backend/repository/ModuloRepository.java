package com.edumatch.backend.repository;

import com.edumatch.backend.model.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {
    List<Modulo> findByRutaIdOrderByOrden(Long rutaId);
    List<Modulo> findByRutaIdAndEstado(Long rutaId, Modulo.Estado estado);
}