package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    List<Persona> findByNombresContainingIgnoreCase(String nombre);
}
