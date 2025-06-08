package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.UbicacionVotacionDTO;
import com.eleccioneselectronicas.dto.ValidacionUbicacionRequestDTO;
import com.eleccioneselectronicas.dto.ValidacionUbicacionResponseDTO;
import com.eleccioneselectronicas.dto.VotoDTO;

import java.util.List;

public interface VotoService {
    VotoDTO emitirVoto(VotoDTO dto);
    List<VotoDTO> listarTodos();
    VotoDTO obtenerPorId(Long id);
    UbicacionVotacionDTO obtenerUbicacionPorCI(String ci);
    ValidacionUbicacionResponseDTO validarUbicacion(String ci, Long idRecinto, ValidacionUbicacionRequestDTO dto);

}
