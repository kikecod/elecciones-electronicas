package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Resultados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultadosRepository extends JpaRepository<Resultados, Long> {
    List<Resultados> findByEleccion_Id(Long idEleccion);
}
