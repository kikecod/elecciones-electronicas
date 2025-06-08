package com.eleccioneselectronicas.controller;

import com.eleccioneselectronicas.dto.ResultadosDTO;
import com.eleccioneselectronicas.service.ResultadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resultados")
@Validated
public class ResultadosController {

    @Autowired
    private ResultadosService service;

    @PostMapping("/generar/{idEleccion}")
    public ResponseEntity<List<ResultadosDTO>> generar(@PathVariable Long idEleccion) {
        return ResponseEntity.ok(service.generarResultados(idEleccion));
    }

    @GetMapping("/eleccion/{idEleccion}")
    public List<ResultadosDTO> listarPorEleccion(@PathVariable Long idEleccion) {
        return service.listarPorEleccion(idEleccion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadosDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}
