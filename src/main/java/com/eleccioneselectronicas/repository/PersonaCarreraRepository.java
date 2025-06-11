package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.PersonaCarrera;
import com.eleccioneselectronicas.model.PersonaCarrera.Rol;
import com.eleccioneselectronicas.model.PersonaCarreraId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonaCarreraRepository extends JpaRepository<PersonaCarrera, PersonaCarreraId> {
    List<PersonaCarrera> findByCarrera_Id(Long carreraId);

    Optional<PersonaCarrera> findByPersonaAndRol(com.eleccioneselectronicas.model.Persona persona, Rol rol);
}
