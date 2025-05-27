package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.RecintoDTO;

import java.util.List;

public interface RecintoService {
    RecintoDTO crear(RecintoDTO dto);
    RecintoDTO obtenerPorId(Long id);
    List<RecintoDTO> listarTodos();
    RecintoDTO actualizar(Long id, RecintoDTO dto);
    void eliminar(Long id);
}
