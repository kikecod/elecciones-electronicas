package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.CarreraDTO;
import com.eleccioneselectronicas.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carreras")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    @GetMapping
    public List<CarreraDTO> listar() {
        return carreraService.listar();
    }

    @GetMapping("/{id}")
    public CarreraDTO obtenerPorId(@PathVariable Long id) {
        return carreraService.obtenerPorId(id);
    }

    @PostMapping
    public CarreraDTO registrar(@RequestBody CarreraDTO dto) {
        return carreraService.registrar(dto);
    }

    @PutMapping("/{id}")
    public CarreraDTO actualizar(@PathVariable Long id, @RequestBody CarreraDTO dto) {
        return carreraService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        carreraService.eliminar(id);
    }

    @GetMapping("/buscar/{nombre}")
    public List<CarreraDTO> buscarPorNombre(@PathVariable String nombre) {
        return carreraService.buscarPorNombre(nombre);
    }
}
