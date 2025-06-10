package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Encargado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EncargadoRepository extends JpaRepository<Encargado, Long> {

    List<Encargado> findByRecinto_IdRecinto(Long idRecinto); // ✅ corregido

    Optional<Encargado> findByPersona_IdPersona(Long idPersona);
    public List<Encargado> findAllByEleccion_Id(Long id);

    // Obtener los encargados por recinto


    // Obtener los encargados por elección y recinto
    @Query("SELECT e FROM Encargado e WHERE e.recinto.id = :idRecinto AND e.eleccion.id = :idEleccion")
    List<Encargado> findByRecintoIdAndEleccionId(Long idRecinto, Long idEleccion);


    
}
