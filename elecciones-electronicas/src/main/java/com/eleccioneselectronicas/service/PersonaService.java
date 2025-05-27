package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.PersonaDTO;
import java.util.List;

public interface PersonaService {
    List<PersonaDTO> listar();
    PersonaDTO obtenerPorId(Long id);
    PersonaDTO registrar(PersonaDTO dto);
    PersonaDTO actualizar(Long id, PersonaDTO dto);
    void eliminar(Long id);
    List<PersonaDTO> buscarPorNombre(String nombre);
}
