package com.eleccioneselectronicas.repository;

import com.eleccioneselectronicas.model.Estudiante;
import com.eleccioneselectronicas.model.PersonaCarrera;
import com.eleccioneselectronicas.model.PersonaCarreraId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonaCarreraRepository extends JpaRepository<PersonaCarrera, PersonaCarreraId> {
    List<PersonaCarrera> findByCarrera_Id(Long carreraId);
}
