package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.FacultadDTO;
import com.eleccioneselectronicas.service.FacultadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facultades")
public class FacultadController {

    @Autowired
    private FacultadService facultadService;

    @GetMapping
    public List<FacultadDTO> listar() {
        return facultadService.listar();
    }

    @GetMapping("/{id}")
    public FacultadDTO obtenerPorId(@PathVariable Long id) {
        return facultadService.obtenerPorId(id);
    }

    @PostMapping
    public FacultadDTO registrar(@RequestBody FacultadDTO dto) {
        return facultadService.registrar(dto);
    }

    @PutMapping("/{id}")
    public FacultadDTO actualizar(@PathVariable Long id, @RequestBody FacultadDTO dto) {
        return facultadService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        facultadService.eliminar(id);
    }

    @GetMapping("/buscar/{nombre}")
    public List<FacultadDTO> buscarPorNombre(@PathVariable String nombre) {
        return facultadService.buscarPorNombre(nombre);
    }
}
