package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    List<Estudiante> findByNombresContainingIgnoreCase(String nombres);
}
