package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartidoRepository extends JpaRepository<Partido, Long> {
    @Query("SELECT p FROM Partido p JOIN Candidato c ON p.idPartido = c.partido.idPartido WHERE c.eleccion.id = :idEleccion")
    List<Partido> findByEleccionId(@Param("idEleccion") Long idEleccion);

}
