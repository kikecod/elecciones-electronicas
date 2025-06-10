package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Persona;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    List<Persona> findByNombreContainingIgnoreCase(String nombre);

    Persona findByCi(String ci);

    Optional<Persona> findByCi(String ci);

}



