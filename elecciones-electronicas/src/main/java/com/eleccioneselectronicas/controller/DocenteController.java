package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.DocenteDTO;
import com.eleccioneselectronicas.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
@CrossOrigin("*")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @GetMapping
    public List<DocenteDTO> listar() {
        return docenteService.listar();
    }

    @GetMapping("/{id}")
    public DocenteDTO obtenerPorId(@PathVariable Long id) {
        return docenteService.obtenerPorId(id);
    }

    @PostMapping
    public DocenteDTO registrar(@RequestBody DocenteDTO dto) {
        return docenteService.registrar(dto);
    }

    @PutMapping("/{id}")
    public DocenteDTO actualizar(@PathVariable Long id, @RequestBody DocenteDTO dto) {
        return docenteService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        docenteService.eliminar(id);
    }

    @GetMapping("/buscar/{nombre}")
    public List<DocenteDTO> buscarPorNombre(@PathVariable String nombre) {
        return docenteService.buscarPorNombre(nombre);
    }
}
