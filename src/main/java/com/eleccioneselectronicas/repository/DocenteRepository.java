package com.eleccioneselectronicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eleccioneselectronicas.model.Docente;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    Boolean existsByCi(String ci);
}
