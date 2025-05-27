package com.eleccioneselectronicas.service;

import java.util.List;

import com.eleccioneselectronicas.dto.EstudianteDTO;

public interface EstudianteService {
    EstudianteDTO crearEstudiante(EstudianteDTO dto);
    EstudianteDTO obtenerEstudiantePorId(Long id);
    List<EstudianteDTO> obtenerEstudiante();
    EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO dto);
    void eliminarEstudiante(Long id);
}
