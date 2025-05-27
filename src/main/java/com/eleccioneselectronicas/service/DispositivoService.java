package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.DispositivoDTO;

import java.util.List;

public interface DispositivoService {
    DispositivoDTO crear(DispositivoDTO dto);
    DispositivoDTO obtenerPorId(Long id);
    List<DispositivoDTO> listarTodos();
    DispositivoDTO actualizar(Long id, DispositivoDTO dto);
    void eliminar(Long id);
}
