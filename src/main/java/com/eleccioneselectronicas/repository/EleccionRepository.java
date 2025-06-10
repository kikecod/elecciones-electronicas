package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Eleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EleccionRepository extends JpaRepository<Eleccion, Long> {
    List<Eleccion> findByNombreContainingIgnoreCase(String nombre);

    Optional<Eleccion> findById(Long id);

}
