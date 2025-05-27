package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.CarreraDTO;

import java.util.List;

public interface CarreraService {
    List<CarreraDTO> listar();
    CarreraDTO obtenerPorId(Long id);
    CarreraDTO registrar(CarreraDTO dto);
    CarreraDTO actualizar(Long id, CarreraDTO dto);
    void eliminar(Long id);
    List<CarreraDTO> buscarPorNombre(String nombre);
}
