package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.FacultadDTO;

import java.util.List;

public interface FacultadService {
    List<FacultadDTO> listar();
    FacultadDTO obtenerPorId(Long id);
    FacultadDTO registrar(FacultadDTO dto);
    FacultadDTO actualizar(Long id, FacultadDTO dto);
    void eliminar(Long id);
    List<FacultadDTO> buscarPorNombre(String nombre);
}
