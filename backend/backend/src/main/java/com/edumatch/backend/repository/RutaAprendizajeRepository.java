package com.edumatch.backend.repository;

import com.edumatch.backend.model.RutaAprendizaje;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RutaAprendizajeRepository extends JpaRepository<RutaAprendizaje, Long> {
    List<RutaAprendizaje> findByEstudianteId(Long estudianteId);
    Optional<RutaAprendizaje> findByEstudianteIdAndActivaTrue(Long estudianteId);
}