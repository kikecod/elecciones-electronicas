package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.AsignacionVotante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsignacionVotanteRepository extends JpaRepository<AsignacionVotante, Long> {



    List<AsignacionVotante> findByVotante_Eleccion_Id(Long idEleccion);
}
