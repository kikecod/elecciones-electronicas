package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Dispositivo;
import com.eleccioneselectronicas.model.Recinto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecintoRepository extends JpaRepository<Recinto, Long> {
    //Optional<Recinto> findById(Long id);

    // Si necesitas obtener los dispositivos por recinto, podrías agregar este método:
    //List<Dispositivo> findByIdRecinto(Long idRecinto);
}
