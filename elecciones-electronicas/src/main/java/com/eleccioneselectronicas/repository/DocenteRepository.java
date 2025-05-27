package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocenteRepository extends JpaRepository<Docente, Long> {
    List<Docente> findByNombresContainingIgnoreCase(String nombres);
}
