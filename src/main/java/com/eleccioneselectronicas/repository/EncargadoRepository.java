package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Encargado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EncargadoRepository extends JpaRepository<Encargado, Long> {

    List<Encargado> findByRecinto_IdRecinto(Long idRecinto); // ✅ corregido

    Optional<Encargado> findByPersona_IdPersona(Long idPersona);
    public List<Encargado> findAllByEleccion_Id(Long id);



    
}
