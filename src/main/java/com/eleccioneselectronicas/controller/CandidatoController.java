package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.CandidatoDTO;
import com.eleccioneselectronicas.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;

    @GetMapping
    public List<CandidatoDTO> listar() {
        return candidatoService.listar();
    }

    @GetMapping("/{id}")
    public CandidatoDTO obtenerPorId(@PathVariable Long id) {
        return candidatoService.obtenerPorId(id);
    }

    @PostMapping
    public CandidatoDTO registrar(@RequestBody CandidatoDTO dto) {
        return candidatoService.registrar(dto);
    }

    @PutMapping("/{id}")
    public CandidatoDTO actualizar(@PathVariable Long id, @RequestBody CandidatoDTO dto) {
        return candidatoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        candidatoService.eliminar(id);
    }

    @GetMapping("/buscar/{cargoPostulado}")
    public List<CandidatoDTO> buscarPorCargo(@PathVariable String cargoPostulado) {
        return candidatoService.buscarPorCargoPostulado(cargoPostulado);
    }
}
