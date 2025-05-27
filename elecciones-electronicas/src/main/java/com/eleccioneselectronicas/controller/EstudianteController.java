package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.EstudianteDTO;
import com.eleccioneselectronicas.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin("*")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public List<EstudianteDTO> listar() {
        return estudianteService.listar();
    }

    @GetMapping("/{id}")
    public EstudianteDTO obtenerPorId(@PathVariable Long id) {
        return estudianteService.obtenerPorId(id);
    }

    @PostMapping
    public EstudianteDTO registrar(@RequestBody EstudianteDTO dto) {
        return estudianteService.registrar(dto);
    }

    @PutMapping("/{id}")
    public EstudianteDTO actualizar(@PathVariable Long id, @RequestBody EstudianteDTO dto) {
        return estudianteService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
    }

    @GetMapping("/buscar/{nombre}")
    public List<EstudianteDTO> buscarPorNombre(@PathVariable String nombre) {
        return estudianteService.buscarPorNombre(nombre);
    }
}
