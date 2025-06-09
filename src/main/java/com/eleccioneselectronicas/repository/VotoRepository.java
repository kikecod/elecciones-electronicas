package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsByVotante_IdVotanteAndEleccion_Id(Long idVotante, Long idEleccion);
    Optional<Voto> findTopByVotante_IdVotanteOrderByEmitidoTsDesc(Long idVotante);
    List<Voto> findByEleccion_Id(Long idEleccion);
}
