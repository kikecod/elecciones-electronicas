package com.eleccioneselectronicas.service;

import java.util.List;

import com.eleccioneselectronicas.dto.DocenteDTO;

public interface DocenteService {
    DocenteDTO crearDocente(DocenteDTO dto);
    DocenteDTO obtenerDocentePorId(Long id);
    List<DocenteDTO> obtenerDocentes();
    DocenteDTO actualizarDocente(Long id, DocenteDTO dto);
    void eliminarDocente(Long id);
}
