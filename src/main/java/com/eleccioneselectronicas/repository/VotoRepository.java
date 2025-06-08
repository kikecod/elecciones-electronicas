package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    boolean existsByVotante_IdVotanteAndEleccion_Id(Long idVotante, Long idEleccion);

    List<Voto> findByEleccion_Id(Long idEleccion);

    long countByEleccionId(Long eleccionId);

    @Query("SELECT v.partido.nombre, COUNT(v) " +
            "FROM Voto v " +
            "WHERE v.eleccion.id = :eleccionId " +
            "GROUP BY v.partido.nombre " +
            "ORDER BY COUNT(v) DESC")
    List<Object[]> totalVotosPorPartidoEnEleccion(@Param("eleccionId") Long eleccionId);

    @Query("SELECT v.partido.nombre, COUNT(v) " +
            "FROM Voto v " +
            "GROUP BY v.partido.nombre " +
            "ORDER BY COUNT(v) DESC")
    List<Object[]> totalVotosPorPartido();

    @Query("SELECT v.partido.nombre, COUNT(v) " +
        "FROM Voto v " +
        "WHERE v.eleccion.id = :eleccionId AND TYPE(v.votante.persona) = :tipoPersona " +
        "GROUP BY v.partido.nombre")
    List<Object[]> totalVotosPorPartidoEnEleccionYRol(@Param("eleccionId") Long eleccionId,
                                                    @Param("tipoPersona") Class<?> tipoPersona);

}
