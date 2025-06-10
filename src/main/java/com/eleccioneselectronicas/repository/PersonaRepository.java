package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Persona;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    List<Persona> findByNombreContainingIgnoreCase(String nombre);
    Optional<Persona> findByCi(String ci);
    

    @Query("SELECT p FROM Persona p " +
            "JOIN PersonaCarrera pc ON p.idPersona = pc.persona.idPersona " +
            "JOIN Carrera c ON pc.carrera.id = c.id " +
            "JOIN Estudiante e ON e.idPersona = p.idPersona " +
            "WHERE c.id = :idCarrera")
    List<Persona> findEstudiantesByCarreraId(@Param("idCarrera") Long idCarrera);

    @Query("SELECT p FROM Persona p " +
            "JOIN PersonaCarrera pc ON p.idPersona = pc.persona.idPersona " +
            "JOIN Facultad c ON pc.carrera.id = c.id " +
            "JOIN Docente e ON e.idPersona = p.idPersona " +
            "WHERE c.id = :idFacultad")
    List<Persona> findDocentesByFacultadId(@Param("idFacultad") Long idFacultad);


}



