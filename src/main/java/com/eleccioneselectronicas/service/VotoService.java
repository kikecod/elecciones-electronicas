package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.*;

import java.util.List;

public interface VotoService {
    VotoDTO emitirVoto(EmitirVotoDTO dto);
    List<VotoDTO> listarTodos();
    VotoDTO obtenerPorId(Long id);
    UbicacionVotacionDTO obtenerUbicacionPorCI(String ci);
    ValidacionUbicacionResponseDTO validarUbicacion(String ci, Long idRecinto, ValidacionUbicacionRequestDTO dto);

}
