package com.edumatch.backend.repository;

import com.edumatch.backend.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DocenteRepository extends JpaRepository<Docente, Long> {
    Optional<Docente> findByUsuarioId(Long usuarioId);
}