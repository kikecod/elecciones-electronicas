package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
}
