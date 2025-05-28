package com.eleccioneselectronicas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eleccioneselectronicas.dto.EstudianteDTO;
import com.eleccioneselectronicas.service.EstudianteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/estudiantes")
@Validated
public class EstudianteController {
    @Autowired
    private EstudianteService service;

    @GetMapping
    public List<EstudianteDTO> obtenerEstudiante() {
        return service.obtenerEstudiante();
    }

    @GetMapping("/{id}")
    public EstudianteDTO obtenerEstudiantePorId(@PathVariable Long id) {
        return service.obtenerEstudiantePorId(id);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED) 
    public EstudianteDTO crearEstudiante(@Valid @RequestBody EstudianteDTO dto) {
        return service.crearEstudiante(dto);
    }

    @PutMapping("/{id}")
    public EstudianteDTO actualizarEstudiante(@PathVariable Long id, @Valid @RequestBody EstudianteDTO dto) {
        return service.actualizarEstudiante(id, dto);
    }

    @PutMapping("/{id}/baja")
    public void eliminarEstudiante(@PathVariable Long id) {
        service.eliminarEstudiante(id);
    }
}
