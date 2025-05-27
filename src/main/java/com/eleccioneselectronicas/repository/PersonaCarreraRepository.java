package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.PersonaCarrera;
import com.eleccioneselectronicas.model.PersonaCarreraId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaCarreraRepository extends JpaRepository<PersonaCarrera, PersonaCarreraId> {
}
