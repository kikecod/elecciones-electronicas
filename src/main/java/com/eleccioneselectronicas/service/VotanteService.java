package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.VotanteDTO;

import java.util.List;

public interface VotanteService {
    List<VotanteDTO> obtenerTodos();
    VotanteDTO obtenerPorId(Long id);
    VotanteDTO crear(VotanteDTO dto);
    VotanteDTO actualizar(Long id, VotanteDTO dto);
    void eliminar(Long id);
}
