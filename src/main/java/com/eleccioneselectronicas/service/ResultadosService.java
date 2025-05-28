package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.ResultadosDTO;

import java.util.List;

public interface ResultadosService {
    List<ResultadosDTO> generarResultados(Long idEleccion);
    List<ResultadosDTO> listarPorEleccion(Long idEleccion);
    ResultadosDTO obtenerPorId(Long id);
}
