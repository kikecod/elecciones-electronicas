package com.eleccioneselectronicas.service;

import java.util.List;

import com.eleccioneselectronicas.dto.PersonaDTO;

public interface PersonaService {
    PersonaDTO buscarPorCi(String ci);
    List<PersonaDTO> filtrarPorRol(String rol);
}
