package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.PersonaCarreraDTO;
import com.eleccioneselectronicas.model.PersonaCarrera;

import java.util.List;

public interface PersonaCarreraService {

    PersonaCarrera crear(PersonaCarreraDTO dto);

    List<PersonaCarrera> listar();

    PersonaCarrera actualizar(Long idPersona, Long idCarrera, PersonaCarreraDTO dto);

    void eliminar(Long idPersona, Long idCarrera);
}
