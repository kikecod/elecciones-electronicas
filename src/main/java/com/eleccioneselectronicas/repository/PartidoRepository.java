package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidoRepository extends JpaRepository<Partido, Long> {
}
