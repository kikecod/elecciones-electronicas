package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    List<Candidato> findByCargoPostuladoContainingIgnoreCase(String cargoPostulado);
}
