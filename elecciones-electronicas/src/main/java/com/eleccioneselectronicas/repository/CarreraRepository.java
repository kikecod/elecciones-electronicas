package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    List<Carrera> findByNombreContainingIgnoreCase(String nombre);
}
