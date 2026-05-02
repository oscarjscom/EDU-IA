package com.edumatch.backend.repository;

import com.edumatch.backend.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    List<Evaluacion> findBySesionId(Long sesionId);
}