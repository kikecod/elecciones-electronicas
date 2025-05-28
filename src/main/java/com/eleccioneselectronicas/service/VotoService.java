package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.VotoDTO;

import java.util.List;

public interface VotoService {
    VotoDTO emitirVoto(VotoDTO dto);
    List<VotoDTO> listarTodos();
    VotoDTO obtenerPorId(Long id);
}
