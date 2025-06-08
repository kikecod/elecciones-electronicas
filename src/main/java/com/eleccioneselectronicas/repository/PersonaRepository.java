package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    List<Persona> findByNombreContainingIgnoreCase(String nombre);
    Optional<Persona> findByCi(String ci);

}
