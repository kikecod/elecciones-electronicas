package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.EleccionDTO;
import com.eleccioneselectronicas.service.EleccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elecciones")
public class EleccionController {

    @Autowired
    private EleccionService eleccionService;

    @GetMapping
    public List<EleccionDTO> listar() {
        return eleccionService.listar();
    }

    @GetMapping("/{id}")
    public EleccionDTO obtenerPorId(@PathVariable Long id) {
        return eleccionService.obtenerPorId(id);
    }

    @PostMapping
    public EleccionDTO registrar(@RequestBody EleccionDTO dto) {
        return eleccionService.registrar(dto);
    }

    @PutMapping("/{id}")
    public EleccionDTO actualizar(@PathVariable Long id, @RequestBody EleccionDTO dto) {
        return eleccionService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        eleccionService.eliminar(id);
    }

    @GetMapping("/buscar/{nombre}")
    public List<EleccionDTO> buscarPorNombre(@PathVariable String nombre) {
        return eleccionService.buscarPorNombre(nombre);
    }
}
