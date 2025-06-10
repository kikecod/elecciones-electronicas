package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    List<Candidato> findByCargoPostuladoContainingIgnoreCase(String cargoPostulado);
    List<Candidato> findByEleccion_Id(Long idEleccion);


    // Si necesitas obtener el partido de cada candidato
    @Query("SELECT c FROM Candidato c WHERE c.eleccion.id = :idEleccion")
    List<Candidato> findCandidatosByEleccionId(Long idEleccion);
}
