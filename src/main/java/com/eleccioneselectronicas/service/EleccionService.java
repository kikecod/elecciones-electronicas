package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.EleccionDTO;

import java.util.List;

public interface EleccionService {
    List<EleccionDTO> listar();
    EleccionDTO obtenerPorId(Long id);
    EleccionDTO registrar(EleccionDTO dto);
    EleccionDTO actualizar(Long id, EleccionDTO dto);
    void eliminar(Long id);
    List<EleccionDTO> buscarPorNombre(String nombre);
}
