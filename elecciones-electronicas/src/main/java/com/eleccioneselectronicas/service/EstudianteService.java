package com.eleccioneselectronicas.service;

import com.eleccioneselectronicas.dto.EstudianteDTO;
import java.util.List;

public interface EstudianteService {
    List<EstudianteDTO> listar();
    EstudianteDTO obtenerPorId(Long id);
    EstudianteDTO registrar(EstudianteDTO dto);
    EstudianteDTO actualizar(Long id, EstudianteDTO dto);
    void eliminar(Long id);
    List<EstudianteDTO> buscarPorNombre(String nombre);
}
