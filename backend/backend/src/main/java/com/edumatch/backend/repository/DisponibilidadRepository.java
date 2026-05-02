package com.edumatch.backend.repository;

import com.edumatch.backend.model.Disponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {
    List<Disponibilidad> findByDocenteId(Long docenteId);
}