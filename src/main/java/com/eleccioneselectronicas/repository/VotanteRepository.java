package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Votante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface VotanteRepository extends JpaRepository<Votante, Long> {
    boolean existsByQrUuid(String qrUuid);
    Optional<Votante> findByPersona_Ci(String ci);

}
