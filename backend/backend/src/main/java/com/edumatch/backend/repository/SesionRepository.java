package com.edumatch.backend.repository;

import com.edumatch.backend.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findByEstudianteId(Long estudianteId);
    List<Sesion> findByDocenteId(Long docenteId);
    List<Sesion> findByEstudianteIdAndEstado(Long estudianteId, Sesion.Estado estado);
    List<Sesion> findByDocenteIdAndEstado(Long docenteId, Sesion.Estado estado);
}