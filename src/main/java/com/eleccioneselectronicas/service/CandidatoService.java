package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.CandidatoDTO;

import java.util.List;

public interface CandidatoService {
    List<CandidatoDTO> listar();
    CandidatoDTO obtenerPorId(Long id);
    CandidatoDTO registrar(CandidatoDTO dto);
    CandidatoDTO actualizar(Long id, CandidatoDTO dto);
    void eliminar(Long id);
    List<CandidatoDTO> buscarPorCargoPostulado(String cargoPostulado);
}
